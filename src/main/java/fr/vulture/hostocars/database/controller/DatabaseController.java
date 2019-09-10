package fr.vulture.hostocars.database.controller;

import static fr.vulture.hostocars.comparator.VersionComparator.VERSION_STRING_REGEX;
import static java.sql.Statement.NO_GENERATED_KEYS;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.BLOB;
import static java.sql.Types.VARCHAR;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import fr.vulture.hostocars.database.builder.Query;
import fr.vulture.hostocars.database.builder.QueryArgument;
import fr.vulture.hostocars.database.builder.QueryBuilder;
import fr.vulture.hostocars.exception.TechnicalException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

/**
 * Global controller for the SQLite Database.
 */
@Controller
public class DatabaseController implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseController.class);

    private static final String DATABASE_INFO_TABLE_NAME = "DatabaseInfo";
    private static final String KEY_COLUMN_NAME = "key";
    private static final String VALUE_COLUMN_NAME = "value";
    private static final String VERSION_VALUE = "version";

    private static final String DATABASE_INITIAL_VERSION = "0.0.0";

    private static final String SQL_COMMENT_DELIMITER = "--";
    private static final String SQL_QUERY_DELIMITER = ";";

    private static final String EMPTY_STRING = "";
    private static final String MULTIPLE_BLANK_REGEX = "\\p{Blank}+";
    private static final String SINGLE_SPACE = " ";

    private final SQLResourceExtractor sqlResourceExtractor;
    private final DatabaseBackupManager databaseBackupManager;

    @NotNull
    @Value("${database.location}")
    private String databaseLocation;

    @NotNull
    @Value("${database.path}")
    private String databasePath;

    @NotNull
    @Value("${database.url}")
    private String databaseUrl;

    @NotNull
    @Pattern(regexp = VERSION_STRING_REGEX)
    @Value("${project.version}")
    private String projectVersion;

    private Connection connection;

    /**
     * Valued autowired constructor.
     *
     * @param sqlResourceExtractor
     *     The autowired {@link SQLResourceExtractor} component
     * @param databaseBackupManager
     *     The autowired {@link DatabaseBackupManager} component
     */
    @Autowired
    public DatabaseController(@NotNull final SQLResourceExtractor sqlResourceExtractor, @NotNull final DatabaseBackupManager databaseBackupManager) {
        this.sqlResourceExtractor = sqlResourceExtractor;
        this.databaseBackupManager = databaseBackupManager;
    }

    @Override
    public void afterPropertiesSet() throws TechnicalException, SQLException, IOException {
        logger.debug("Initializing the database connection");

        // If the folder doesn't exist, creates it
        final File dataFolder = new File(databaseLocation);
        if (!dataFolder.exists() || !dataFolder.isDirectory()) {
            dataFolder.mkdir();
            logger.debug("Database folder created with name {}", databaseLocation);
        }

        // If the database file doesn't exist yet, an initialization is needed
        final boolean isInitializationNeeded = !new File(databasePath).exists();

        // Connects to the database
        connection = DriverManager.getConnection(databaseUrl);

        if (isInitializationNeeded) {
            // Initializes the database to current version
            updateDatabaseToCurrentVersion(DATABASE_INITIAL_VERSION);
        } else {
            // Retrieves the database version
            final String databaseVersion = getCurrentDatabaseVersion();

            if (isNull(databaseVersion)) {
                // If no version in available in the database, throws an exception
                throw new TechnicalException("Unable to retrieve the database version");
            } else if (databaseVersion.compareTo(projectVersion) > 0) {
                // If the database version is higher than the project version, throws an exception
                throw new TechnicalException("The database version is higher than the project one");
            } else if (databaseVersion.compareTo(projectVersion) < 0) {
                // If the database version is lower than the project version, updates the database to the project version
                updateDatabaseToCurrentVersion(databaseVersion);
            } else {
                // Backups the database
                databaseBackupManager.backupDatabase(false);
            }
        }

        logger.info("Connection to database established");
    }

    /**
     * Generates a {@link PreparedStatement} from the database connection and the input {@link Query}, and set the query's arguments.
     *
     * @param query
     *     The {@link Query} from which to create the {@link PreparedStatement}
     * @param withGeneratedKeys
     *     If the {@link PreparedStatement} has to return the eventual generated keys
     *
     * @return a {@link PreparedStatement}
     *
     * @throws TechnicalException
     *     if an error occurs while reading a file or generating the statement
     */
    public PreparedStatement prepareStatement(@NotNull @Valid final Query query, final boolean withGeneratedKeys) throws TechnicalException {
        // Creates the statement from the query
        final PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query.getQuery(), withGeneratedKeys ? RETURN_GENERATED_KEYS : NO_GENERATED_KEYS);

            // For each query argument, set its value to the statement depending on its type
            int index = 1;
            for (final QueryArgument argument : query.getArguments()) {
                // The case of a BLOB query argument needs to be treated apart from the others, as it has to be read from the input URL
                if (argument.getType() == BLOB && nonNull(argument.getValue())) {
                    try {
                        statement.setBytes(index++, Files.readAllBytes(Paths.get((String) argument.getValue())));
                    } catch (IOException e) {
                        throw new TechnicalException(e, "Error while reading the file \"{}\"", argument.getValue());
                    }
                } else {
                    statement.setObject(index++, argument.getValue(), argument.getType());
                }
            }
        } catch (SQLException e) {
            throw new TechnicalException(e, "Error while generating the SQL statement");
        }

        // Returns the now fully prepared statement
        return statement;
    }

    /**
     * Retrieves the current database version.
     *
     * @return the current database version
     *
     * @throws SQLException
     *     if an error occurs while executing the SQL statement or retrieving its results
     * @throws TechnicalException
     *     if an error occurs while generating the SQL statement
     */
    private String getCurrentDatabaseVersion() throws SQLException, TechnicalException {
        logger.debug("Retrieving the current database version");

        // Builds the query
        final List<String> columnNames = Collections.singletonList(VALUE_COLUMN_NAME);
        final List<QueryArgument> arguments = Collections.singletonList(new QueryArgument(KEY_COLUMN_NAME, VERSION_VALUE, VARCHAR));
        final Query query = new QueryBuilder().buildSelectQuery(DATABASE_INFO_TABLE_NAME, columnNames, false).addWhereClause(arguments).getQuery();

        // Prepares the statement
        final PreparedStatement statement = prepareStatement(query, false);

        // Executes the statement
        final ResultSet result = statement.executeQuery();

        // Retrieves and returns the version
        if (result.next()) {
            final String version = result.getString(VALUE_COLUMN_NAME);

            logger.debug("The current database version ({}) has been successfully retrieved", version);
            return version;
        }

        return null;
    }

    /**
     * Updates the database from its current version to the project one by executing the executable SQL resources.
     *
     * @param databaseVersion
     *     The current database version
     *
     * @throws IOException
     *     if an I/O error occurs while extracting the resources or while executing a script
     * @throws SQLException
     *     if an SQL error occurs while executing a script
     * @throws TechnicalException
     *     if a technical error occurs while executing a script
     */
    private void updateDatabaseToCurrentVersion(@NotNull final String databaseVersion) throws IOException, SQLException, TechnicalException {
        logger.debug("Updating the database from version {} to version {}", databaseVersion, projectVersion);

        // Backups the database
        databaseBackupManager.backupDatabase(true);

        // Extracts the SQL resources
        final SortedMap<String, SortedSet<Resource>> resources = sqlResourceExtractor.extractSQLResources(databaseVersion, projectVersion);

        for (final String version : resources.keySet()) {
            for (final Resource resource : resources.get(version)) {
                executeScript(resource);
            }

            logger.debug("Database successfully updated to version {}", version);
        }

        logger.info("Database successfully updated from version {} to version {}", databaseVersion, projectVersion);
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
     *     if an SQL error occurs while executing the statement
     * @throws TechnicalException
     *     if a technical error occurs while generating the statement
     */
    private void executeScript(@NotNull final Resource resource) throws IOException, SQLException, TechnicalException {
        logger.debug("Executing script file {}", resource.getFilename());

        // Initializes the buffered reader for the script file
        final BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

        // While the buffered reader has more lines to read, keeps reading the script file
        String line;
        String queryString = EMPTY_STRING;
        while (nonNull(line = reader.readLine())) {
            // Ignores the lines that are comments
            if (!line.startsWith(SQL_COMMENT_DELIMITER)) {
                // Removes the comments at the end of the line
                line = line.split(SQL_COMMENT_DELIMITER)[0];

                // Adds the current line to the current query (separated with a space)
                queryString = queryString.concat(SINGLE_SPACE).concat(line);

                // If the query ends with a query delimiter, executes the query
                if (queryString.endsWith(SQL_QUERY_DELIMITER)) {
                    // Removes useless spaces
                    queryString = queryString.trim().replaceAll(MULTIPLE_BLANK_REGEX, SINGLE_SPACE);

                    logger.debug("Executing query \"{}\"", queryString);

                    // Creates the current query statement
                    final Query query = new Query();
                    query.setQuery(queryString);
                    final PreparedStatement statement = prepareStatement(query, false);

                    // Executes the statement
                    statement.execute();

                    logger.debug("The query has been successfully executed");

                    // Re-initializes the query
                    queryString = EMPTY_STRING;
                }
            }
        }

        logger.debug("Script file {} successfully executed", resource.getFilename());
    }

}
