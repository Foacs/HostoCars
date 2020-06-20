package fr.vulture.hostocars.database;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.sql.DriverManager.getConnection;
import static java.util.Objects.nonNull;
import static org.slf4j.helpers.MessageFormatter.arrayFormat;

import fr.vulture.hostocars.entity.PropertyEntity;
import fr.vulture.hostocars.repository.PropertyRepository;
import fr.vulture.hostocars.system.ResourceExtractor;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.SortedMap;
import java.util.SortedSet;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.util.Version;
import org.springframework.stereotype.Component;

/**
 * Initializer for the SQLite database.
 */
@Slf4j
@Component
class DatabaseInitializer implements InitializingBean {

    /**
     * The initial database version to use in case of initialization ({@code 0.0.0.0}).
     */
    private static final Version INITIAL_VERSION = new Version(0);

    @NonNull
    private final ResourceExtractor resourceExtractor;

    @NonNull
    private final BackupManager backupManager;

    @NonNull
    private final PropertyRepository propertyRepository;

    @NonNull
    private final Version projectVersion;

    @NonNull
    @Value("${database.location}")
    private String databaseLocation;

    @NonNull
    @Value("${database.path}")
    private String databasePath;

    @NonNull
    @Value("${spring.datasource.url}")
    private String databaseUrl;

    /**
     * Valued autowired constructor.
     *
     * @param propertyRepository
     *     The autowired {@link PropertyRepository} component
     * @param resourceExtractor
     *     The autowired {@link ResourceExtractor} component
     * @param backupManager
     *     The autowired {@link BackupManager} component
     * @param projectVersion
     *     The {@code project.version} property
     */
    @Autowired
    DatabaseInitializer(final PropertyRepository propertyRepository, final ResourceExtractor resourceExtractor, final BackupManager backupManager,
        @Value("${project.version}") final String projectVersion) {
        this.propertyRepository = propertyRepository;
        this.resourceExtractor = resourceExtractor;
        this.backupManager = backupManager;
        this.projectVersion = Version.parse(projectVersion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() throws IOException, SQLException {
        log.info("Initializing the database");

        // If the folder doesn't exist, creates it
        final File dataFolder = new File(this.databaseLocation);
        if (!dataFolder.exists() || !dataFolder.isDirectory()) {
            dataFolder.mkdir();
            log.debug("Database folder created");
        }

        if (new File(this.databasePath).exists()) {
            // Retrieves the current database version property
            final Optional<PropertyEntity> databaseVersionProperty = this.propertyRepository.findByKey("version");

            // If the database version property is missing, throws an exception
            assert databaseVersionProperty.isPresent() : "The database version property is missing from the database";

            // Extracts the database version from its property
            final Version databaseVersion = Version.parse(databaseVersionProperty.get().getValue());

            // If the database version is higher than the project version, throws an exception
            assert 0 >= databaseVersion.compareTo(this.projectVersion) : arrayFormat("The database version ({}) is higher than the project one ({})",
                new Object[] {databaseVersion, this.projectVersion}).getMessage();

            if (0 > databaseVersion.compareTo(this.projectVersion)) {
                // If the database version is lower than the project version, updates the database
                this.updateDatabaseToProjectVersion(databaseVersion);
            } else {
                // Backups the database if needed
                this.backupManager.backupDatabaseIfNeeded();
            }
        } else {
            // Initializes the database from scratch to current version
            this.updateDatabaseToProjectVersion(INITIAL_VERSION);
        }

        log.info("Database initialized");
    }

    /**
     * Updates the database from its current version to the project one by executing the executable SQL resources.
     *
     * @param currentVersion
     *     The current database version
     *
     * @throws IOException
     *     if an I/O error occurs while extracting or reading the resources
     * @throws SQLException
     *     if an SQL error occurs while executing the resource scripts
     */
    private void updateDatabaseToProjectVersion(@NonNull final Version currentVersion) throws IOException, SQLException {
        log.info("Updating the database from version {} to version {}", currentVersion, this.projectVersion);

        // Backups the database if the version is not initial
        if (0 < currentVersion.compareTo(INITIAL_VERSION)) {
            this.backupManager.backupDatabaseForUpdate();
        }

        // Extracts the SQL resources by version
        final SortedMap<Version, SortedSet<Resource>> resourceMap = this.resourceExtractor.extractSQLResources(currentVersion, this.projectVersion);

        for (final Entry<Version, SortedSet<Resource>> entry : resourceMap.entrySet()) {
            // If the version is strictly higher than the current one and lower or equal to the project version, executes its scripts
            final Version version = entry.getKey();
            if (0 < version.compareTo(currentVersion) && 0 >= version.compareTo(this.projectVersion)) {
                for (final Resource resource : entry.getValue()) {
                    this.executeScript(resource);
                }

                log.info("Database successfully updated to version {}", version);
            }
        }
    }

    /**
     * Parses an executable SQL script resource and executes its queries.
     *
     * @param resource
     *     The executable SQL script resource
     *
     * @throws IOException
     *     if an I/O error occurs while reading the script file
     * @throws SQLException
     *     if an SQL error occurs while executing the resource script
     */
    private void executeScript(@NonNull final Resource resource) throws IOException, SQLException {
        log.debug("Executing script file : {}", resource.getFilename());

        // Initializes the buffered reader for the script file
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), UTF_8))) {
            String line;
            String queryString = "";

            // While the buffered reader has more lines to read, keeps reading the script file
            while (nonNull(line = reader.readLine())) {
                // Ignores the lines that are comments
                if (!line.startsWith("--")) {
                    // Removes the comments at the end of the line
                    line = line.split("--")[0];

                    // Adds the current line to the current query (separated with a space)
                    queryString = queryString.concat(" ").concat(line);

                    // If the query ends with a query delimiter, executes the query
                    if (queryString.endsWith(";")) {
                        // Removes useless spaces
                        queryString = queryString.trim().replaceAll("\\p{Blank}+", " ");

                        // Opens a connection and disables the auto-commit feature
                        try (final Connection connection = getConnection(this.databaseUrl)) {
                            connection.setAutoCommit(false);

                            // Creates a statement
                            try (final Statement statement = connection.createStatement()) {
                                // Executes the query
                                log.trace("Executing query : {}", queryString);
                                log.trace("Execution output : {}", statement.executeUpdate(queryString));
                            }

                            // Commits the changes and closes the connection
                            connection.commit();
                        }

                        // Re-initializes the query
                        queryString = "";
                    }
                }
            }

            log.debug("Script file \"{}\" executed", resource.getFilename());
        }
    }

}
