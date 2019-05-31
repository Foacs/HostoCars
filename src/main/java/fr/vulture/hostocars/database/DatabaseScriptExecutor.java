package fr.vulture.hostocars.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * Utility component for executing SQL scripts on the database from files.
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
     * @throws IOException
     *     if a script file can not be found or read
     * @throws SQLException
     *     if a script file execution fails
     */
    void initializeDatabaseToCurrentVersion() throws IOException, SQLException {
        logger.debug("Initializing the database to version {}", projectVersion);

        File resources = new ClassPathResource(sqlScriptsResourcesLocation).getFile();
        File[] versions = resources.listFiles(file -> file.isDirectory()
            && file.getName().compareTo(projectVersion) <= 0);

        updateDatabaseToVersions(versions);

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
     */
    void updateDatabaseToCurrentVersion(String databaseVersion) throws IOException, SQLException {
        File resources = new ClassPathResource(sqlScriptsResourcesLocation).getFile();
        File[] versions = resources.listFiles(file -> file.isDirectory()
            && file.getName().compareTo(projectVersion) <= 0
            && file.getName().compareTo(databaseVersion) > 0);

        updateDatabaseToVersions(versions);
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
    private void updateDatabaseToVersions(File[] versions) throws IOException, SQLException {
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

    /**
     * Parses a SQL script file and executes its queries.
     *
     * @param script
     *     The SQL script file
     *
     * @throws IOException
     *     if the script file can not be found or read
     * @throws SQLException
     *     if the script file execution fails
     */
    private void executeScript(File script) throws IOException, SQLException {
        logger.debug("Executing script file {}", script.getName());

        String query = "";
        BufferedReader reader = new BufferedReader(new FileReader(script));
        String line;
        while ((line = reader.readLine()) != null) {
            // Ignores comments
            if (!line.startsWith("--")) {
                query = query.concat(line);

                // Executes the query on delimiter found
                if (query.endsWith(";")) {
                    // Removes useless spaces
                    query = query.trim().replaceAll(" {2}", " ");

                    logger.debug("Executing query \"{}\"", query);

                    final Statement statement = connection.createStatement();
                    statement.execute(query);

                    logger.debug("Query successfully executed");

                    // Re-initializes the query
                    query = "";
                }
            }
        }
    }

}
