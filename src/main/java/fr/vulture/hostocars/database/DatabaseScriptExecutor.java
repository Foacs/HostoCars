package fr.vulture.hostocars.database;

import static fr.vulture.hostocars.ProfileEnum.*;
import static fr.vulture.hostocars.util.StringUtils.*;
import static java.util.Objects.*;

import fr.vulture.hostocars.ProfileEnum;
import fr.vulture.hostocars.error.TechnicalException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

/**
 * Utility component for executing SQL scripts on the database from files.
 */
@Component("databaseScriptExecutor")
class DatabaseScriptExecutor implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseScriptExecutor.class);

    private static final Pattern versionPattern = Pattern.compile(VERSION_STRING_REGEX);

    @Value("${project.version}")
    private String projectVersion;

    @Value("${sql.scripts.extension}")
    private String sqlScriptsExtension;

    @Value("${sql.scripts.resources.location}")
    private String sqlScriptsResourcesLocation;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    private DatabaseConnection connection;

    @Override
    public void afterPropertiesSet() {
        logger.debug("Initializing {}", logger.getName());

        if (isNull(projectVersion)) {
            throw new InvalidConfigurationPropertyValueException("project.version", null, "Missing value");
        }

        if (!projectVersion.matches(VERSION_STRING_REGEX)) {
            throw new InvalidConfigurationPropertyValueException("project.version", projectVersion, "Bad format");
        }

        if (isNull(sqlScriptsExtension)) {
            throw new InvalidConfigurationPropertyValueException("sql.scripts.extension", null, "Missing value");
        }

        if (isNull(sqlScriptsResourcesLocation)) {
            throw new InvalidConfigurationPropertyValueException("sql.scripts.resources.location", null, "Missing value");
        }

        if (isNull(profile)) {
            throw new InvalidConfigurationPropertyValueException("spring.profiles.active", null, "Missing value");
        }

        if (isNull(ProfileEnum.of(profile))) {
            throw new InvalidConfigurationPropertyValueException("spring.profiles.active", null, "Unknown profile");
        }
    }

    /**
     * Initializes the database from scratch to the current project version.
     *
     * @throws IOException
     *     if a script file can not be found or read
     * @throws SQLException
     *     if a script file execution fails
     * @throws TechnicalException
     *     if no script folder version is found
     */
    void initializeDatabaseToCurrentVersion() throws IOException, SQLException, TechnicalException {
        logger.debug("Initializing the database to version {}", projectVersion);

        // Retrieves the SQL resources
        final List<Resource> resources = extractSQLResources();

        // If no SQL resource has been found, throws a technical exception
        if (resources.isEmpty()) {
            throw new TechnicalException("No SQL resource found");
        }

        // Extract the distinct versions from the SQL resources
        List<String> versions = extractVersionsFromResources(resources);

        // Filters the versions to exclude the ones following the project version
        versions = versions.stream()
            .filter(version -> versionComparator.compare(version, projectVersion) <= 0)
            .collect(Collectors.toList());

        // If no SQL script folder version has been found, throws a technical exception
        if (versions.isEmpty()) {
            throw new TechnicalException("No script folder version found");
        }

        // Updates the database version
        updateDatabaseToVersions(resources, versions);
    }

    /**
     * Updates the database from its current version to the current project version.
     *
     * @param databaseVersion
     *     The current database version
     *
     * @throws IOException
     *     if a script file can not be found or read
     * @throws SQLException
     *     if a script file execution fails
     * @throws TechnicalException
     *     if no higher script folder version is found
     */
    void updateDatabaseToCurrentVersion(@NotNull String databaseVersion) throws IOException, SQLException, TechnicalException {
        logger.debug("Updating the database from version {} to version {}", databaseVersion, projectVersion);

        // Retrieves the SQL resources
        final List<Resource> resources = extractSQLResources();

        // Extract the distinct versions from the SQL resources
        List<String> versions = extractVersionsFromResources(resources);

        // Filters the versions to exclude the ones preceding the database version and following the project version
        versions = versions.stream()
            .filter(version -> versionComparator.compare(version, databaseVersion) > 0)
            .filter(version -> versionComparator.compare(version, projectVersion) <= 0)
            .collect(Collectors.toList());

        // If no SQL script folder version has been found, throws a technical exception
        if (versions.isEmpty()) {
            throw new TechnicalException("No higher script folder version found");
        }

        // Updates the database version
        updateDatabaseToVersions(resources, versions);
    }

    /**
     * Returns the SQL resources extracted from the SQL resource location.
     *
     * @return a list of resources
     *
     * @throws IOException
     *     if the extraction fails
     */
    private List<Resource> extractSQLResources() throws IOException {
        final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        return Arrays.asList(resolver.getResources("classpath:" + sqlScriptsResourcesLocation + "/**"));
    }

    /**
     * Extracts the list of sorted distinct database versions from a list of resources, depending on the active profile.
     *
     * @param resources
     *     The SQL resources
     *
     * @return a list of sorted distinct database versions
     */
    private List<String> extractVersionsFromResources(@NotNull final List<Resource> resources) {
        final ProfileEnum activeProfile = ProfileEnum.of(profile);

        // Extracts the SQL resources paths depending on the active profile
        List<String> resourcesPaths = new ArrayList<>();
        if (PROD.equals(activeProfile)) {
            resourcesPaths = resources.stream()
                .map(resource -> ((ClassPathResource) resource).getPath())
                .collect(Collectors.toList());
        } else if (DEV.equals(activeProfile)) {
            resourcesPaths = resources.stream()
                .map(resource -> ((FileSystemResource) resource).getPath())
                .collect(Collectors.toList());
        }

        // Extracts the versions from the SQL resources paths
        final List<String> versions = new ArrayList<>();
        for (final String resourcePath : resourcesPaths) {
            final Matcher matcher = versionPattern.matcher(resourcePath);

            if (matcher.find()) {
                // If the matcher found a matching version, adds it
                versions.add(matcher.group(0));
            }
        }

        // Returns the distinct sorted versions
        return versions.stream().distinct().sorted(versionComparator).collect(Collectors.toList());
    }

    /**
     * Updates the database with the given SQL scripts resources versions folders.
     *
     * @param versions
     *     The versions folders
     *
     * @throws IOException
     *     if a script file can not be found or read
     * @throws SQLException
     *     if a script file execution fails
     */
    private void updateDatabaseToVersions(@NotNull List<Resource> resources, @NotNull List<String> versions) throws IOException, SQLException {
        for (String version : versions) {
            logger.debug("Updating the database to version {}", version);

            // Extracts the SQL executable resources for the current version
            List<Resource> versionResources = extractExecutableResourcesByVersion(resources, version);

            if (versionResources.isEmpty()) {
                // If there is no resource to process, logs a warning
                logger.warn("No SQL script found for database version {}", version);
            } else {
                // Else, executes all of them
                for (Resource resource : versionResources) {
                    executeScript(resource);
                }
            }

            logger.debug("The database has been successfully updated to version {}", version);
        }
    }

    /**
     * Extracts the list of sorted SQL executable resources for a given version, depending on the active profile.
     *
     * @param resources
     *     The resources
     * @param version
     *     The version
     *
     * @return a list of sorted SQL executable resources
     */
    private List<Resource> extractExecutableResourcesByVersion(@NotNull final List<Resource> resources, @NotNull final String version) {
        final ProfileEnum activeProfile = ProfileEnum.valueOf(profile.toUpperCase());

        // Extracts the SQL resources for the version depending on the active profile
        List<Resource> versionResources = new ArrayList<>();
        if (PROD.equals(activeProfile)) {
            versionResources = resources.stream()
                .filter(resource -> ((ClassPathResource) resource).getPath().contains(version))
                .filter(resource -> ((ClassPathResource) resource).getPath().endsWith(sqlScriptsExtension))
                .collect(Collectors.toList());
        } else if (DEV.equals(activeProfile)) {
            versionResources = resources.stream()
                .filter(resource -> ((FileSystemResource) resource).getPath().contains(version))
                .filter(resource -> ((FileSystemResource) resource).getPath().endsWith(sqlScriptsExtension))
                .collect(Collectors.toList());
        }

        // Returns the sorted list of found SQL resources for the version
        return versionResources.stream()
            .filter(resource -> nonNull(resource.getFilename()))
            .sorted(Comparator.comparing(Resource::getFilename))
            .collect(Collectors.toList());
    }

    /**
     * Parses a SQL script resource and executes its queries.
     *
     * @param resource
     *     The SQL script resource
     *
     * @throws IOException
     *     if the script resource can not be found or read
     * @throws SQLException
     *     if the script resource execution fails
     */
    private void executeScript(@NotNull Resource resource) throws IOException, SQLException {
        logger.debug("Executing script file {}", resource.getFilename());

        String line;
        String query = "";
        final BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        while (nonNull(line = reader.readLine())) {
            // Ignores comments
            if (!line.startsWith("--")) {
                query = query.concat(line);

                // Executes the query on delimiter found
                if (query.endsWith(";")) {
                    // Removes useless spaces
                    query = query.trim().replaceAll("\\p{Blank}+", " ");

                    logger.debug("Executing query \"{}\"", query);

                    // Creates the current query statement
                    final Statement statement = connection.createStatement();

                    // Executes the current query
                    statement.execute(query);

                    logger.debug("The query has been successfully executed");

                    // Re-initializes the query
                    query = "";
                }
            }
        }
    }

}
