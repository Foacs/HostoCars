package fr.vulture.hostocars.database;

import static java.util.Objects.*;

import fr.vulture.hostocars.error.TechnicalException;
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
     * @throws TechnicalException
     *     if no higher script folder version is found
     */
    void initializeDatabaseToCurrentVersion() throws IOException, SQLException, TechnicalException {
        logger.debug("Initializing the database to version {}", projectVersion);

        File resources = new ClassPathResource(sqlScriptsResourcesLocation).getFile();
        File[] versions = resources.listFiles(file -> file.isDirectory()
            && file.getName().compareTo(projectVersion) <= 0);

        if (isNull(versions)) {
            throw new TechnicalException("No higher script folder version found");
        }

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
     * @throws TechnicalException
     *     if no higher script folder version is found
     */
    void updateDatabaseToCurrentVersion(String databaseVersion) throws IOException, SQLException, TechnicalException {
        logger.debug("Updating the database from version {} to version {}", projectVersion,
            databaseVersion);

        File resources = new ClassPathResource(sqlScriptsResourcesLocation).getFile();
        File[] versions = resources.listFiles(file -> file.isDirectory()
            && file.getName().compareTo(projectVersion) <= 0
            && file.getName().compareTo(databaseVersion) > 0);

        if (isNull(versions)) {
            throw new TechnicalException("No higher script folder version found");
        }

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

            if (isNull(scripts)) {
                logger.warn("No SQL script found for database version {}", version.getName());
            } else {
                for (File script : scripts) {
                    executeScript(script);
                }

                logger.debug("The database has been successfully updated to version {}",
                    version.getName());
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
        while (nonNull(line = reader.readLine())) {
            // Ignores comments
            if (!line.startsWith("--")) {
                query = query.concat(line);

                // Executes the query on delimiter found
                if (query.endsWith(";")) {
                    // Removes useless spaces
                    query = query.trim().replaceAll(" {2}", " ");

                    logger.debug("Executing query {}", query);

                    final Statement statement = connection.createStatement();
                    statement.execute(query);

                    logger.debug("The query has been successfully executed");

                    // Re-initializes the query
                    query = "";
                }
            }
        }
    }

}
