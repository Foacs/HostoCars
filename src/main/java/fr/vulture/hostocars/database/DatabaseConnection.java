package fr.vulture.hostocars.database;

import fr.vulture.hostocars.controller.DatabaseController;
import fr.vulture.hostocars.error.TechnicalException;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Connection component to the SQLite Database.
 */
@Component("databaseConnection")
public class DatabaseConnection implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    private Connection connection;

    @Value("${database.location}")
    private String databaseLocation;

    @Value("${database.path}")
    private String databasePath;

    @Value("${database.url}")
    private String databaseUrl;

    @Value("${project.version}")
    private String projectVersion;

    @Autowired
    private DatabaseController databaseController;

    @Autowired
    private DatabaseScriptExecutor databaseScriptExecutor;

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() throws TechnicalException, SQLException {
        logger.debug("Initializing {}", logger.getName());

        final File dataFolder = new File(databaseLocation);
        if (!dataFolder.exists() || !dataFolder.isDirectory()) {
            // If the folder doesn't exist, creates it
            dataFolder.mkdir();

            logger.debug("\"{}\" folder has been created", databaseLocation);
        }

        // If the database file doesn't exist yet, an initialization is needed
        final boolean isInitializationNeeded = !new File(databasePath).exists();

        // Connects to the database
        connection = DriverManager.getConnection(databaseUrl);

        String databaseVersion;
        if (isInitializationNeeded) {
            // Initializes the database to current version
            databaseScriptExecutor.initializeDatabaseToCurrentVersion();
            databaseVersion = projectVersion;

            logger.info("Database initialized to version {}", projectVersion);
        } else {
            // Retrieves the database version
            databaseVersion = databaseController.getCurrentDatabaseVersion();

            if (databaseVersion == null) {
                // If no version in available in the database, throws an error
                throw new TechnicalException("Unable to retrieve the database version");
            } else if (databaseVersion.compareTo(projectVersion) > 0) {
                // If the database version is higher than the project version, throws an error
                throw new TechnicalException("The database version is higher than the project version");
            } else if (databaseVersion.compareTo(projectVersion) < 0) {
                // If the database version is lower than the project version, updates the database
                databaseScriptExecutor.updateDatabaseToCurrentVersion(databaseVersion);

                logger.info("Database updated from version {} to version {}", databaseVersion, projectVersion);

                databaseVersion = projectVersion;
            }
        }

        logger.info("Connection to database established (version: {})", databaseVersion);
    }

    /**
     * Calls {@link Connection#createStatement()}.
     */
    Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    /**
     * Calls {@link Connection#prepareStatement(String)}.
     */
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

}
