package fr.vulture.hostocars.controller;

import fr.vulture.hostocars.database.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Global controller for the SQLite Database
 */
@Controller
public class DatabaseController {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseController.class);

    @Autowired
    private DatabaseConnection connection;

    /**
     * Retrieves the current database version.
     *
     * @return the current database version
     */
    public String getCurrentDatabaseVersion() {
        logger.debug("Calling getCurrentDatabaseVersion");

        // Prepares the query
        final String query = "SELECT value FROM DatabaseInfo WHERE key = 'version'";

        String version = null;
        try {
            // Prepares the statement
            final PreparedStatement statement = connection.prepareStatement(query);
            // Executes the query
            final ResultSet result = statement.executeQuery();
            // Retrieves the resultant version
            while (result.next()) {
                version = result.getString("value");
                logger.debug(" > {}", version);
            }

            logger.debug("Call to getCurrentDatabaseVersion ended successfully");
        } catch (SQLException e) {
            logger.error("Error while calling getCurrentDatabaseVersion", e);
        }

        return version;
    }

}
