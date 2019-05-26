package fr.vulture.hostocars.database;

import fr.vulture.hostocars.error.TechnicalException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * Utility component for executing SQL scripts on the database from files
 */
@Component("databaseScriptExecutor")
class DatabaseScriptExecutor {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseScriptExecutor.class);

    @Value("${project.version}")
    private String projectVersion;

    @Value("${sql.scripts.extension}")
    private String sqlScriptsExtension;

    @Value("${sql.scripts.resources.location}")
    private String sqlScriptsResourcesLocation;

    @Autowired
    private DatabaseConnection connection;

    /**
     * Initializes the database from scratch to the current project version.
     *
     * @throws TechnicalException
     *     if the initialization fails
     */
    void initializeDatabaseToCurrentVersion() throws TechnicalException {
        logger.debug("Initializing the database to version {}", projectVersion);

        try {
            File resources = new ClassPathResource(sqlScriptsResourcesLocation).getFile();
            File[] versions = resources.listFiles(file -> file.isDirectory()
                && file.getName().compareTo(projectVersion) <= 0);

            updateDatabaseToVersions(versions);
        } catch (IOException e) {
            throw new TechnicalException("SQL scripts resources not found", e);
        }
    }

    /**
     * Updates the database from its current version to the current project version.
     *
     * @param databaseVersion
     *     The current database version
     *
     * @throws TechnicalException
     *     if the update fails
     */
    void updateDatabaseToCurrentVersion(String databaseVersion) throws TechnicalException {
        try {
            File resources = new ClassPathResource(sqlScriptsResourcesLocation).getFile();
            File[] versions = resources.listFiles(file -> file.isDirectory()
                && file.getName().compareTo(projectVersion) <= 0
                && file.getName().compareTo(databaseVersion) > 0);

            updateDatabaseToVersions(versions);
        } catch (IOException e) {
            throw new TechnicalException("SQL scripts resources not found", e);
        }
    }

    /**
     * Updates the database with the given SQL scripts resources versions folders.
     *
     * @param versions
     *     The versions folders
     *
     * @throws TechnicalException
     *     if the updates fails
     */
    private void updateDatabaseToVersions(File[] versions) throws TechnicalException {
        if (versions == null) {
            throw new TechnicalException(MessageFormat.format("No SQL resources scripts folder found for version {0} or under", projectVersion));
        } else {
            for (File version : versions) {
                logger.debug("Updating the database to version {}", version.getName());

                File[] scripts = version.listFiles(file -> !file.isDirectory() && file.getName().endsWith(sqlScriptsExtension));

                if (scripts == null) {
                    logger.warn("No SQL script found for database version {}", version.getName());
                } else {
                    for (File script : scripts) {
                        executeScript(script);
                    }

                    logger.debug("Database successfully updated to version {}", version.getName());
                }
            }
        }
    }

    /**
     * Parses a SQL script file and executes its queries.
     *
     * @param script
     *     The SQL script file
     *
     * @throws TechnicalException
     *     if the execution fails
     */
    private void executeScript(File script) throws TechnicalException {
        logger.debug("Executing script file {}", script.getName());

        String query = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(script))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Ignores comments
                if (!line.startsWith("--")) {
                    query = query.concat(line);

                    // Executes the query on delimiter found
                    if (query.endsWith(";")) {
                        // Removes useless spaces
                        query = query.trim().replaceAll("  ", " ");

                        logger.debug("Executing query \"{}\"", query);

                        final Statement statement = connection.createStatement();
                        statement.execute(query);

                        logger.debug("Query successfully executed");

                        // Re-initializes the query
                        query = "";
                    }
                }
            }
        } catch (IOException e) {
            throw new TechnicalException(MessageFormat.format("Unable to parse script file {0}", script.getName()), e);
        } catch (SQLException e) {
            throw new TechnicalException(MessageFormat.format("Error while executing query {0} from script file {1}", query, script.getName()), e);
        }
    }

}
