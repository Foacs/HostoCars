package fr.vulture.hostocars.controller;

import fr.vulture.hostocars.database.DatabaseConnection;
import fr.vulture.hostocars.error.TechnicalException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Global controller for the SQLite Database.
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
     *
     * @throws SQLException
     *     if an error occurs while executing the query
     * @throws TechnicalException
     *     if no version or more than one version are found
     */
    public String getCurrentDatabaseVersion() throws SQLException, TechnicalException {
        logger.debug("Calling getCurrentDatabaseVersion");

        // Prepares the query
        final String query = "SELECT value FROM DatabaseInfo WHERE key = 'version'";

        // Prepares the statement
        final PreparedStatement statement = connection.prepareStatement(query);

        // Executes the query
        final ResultSet result = statement.executeQuery();

        // Retrieves the resultant version
        String version;
        if (result.next()) {
            version = result.getString("value");

            if (result.next()) {
                // If there is more than one result, throw a technical exception
                throw new TechnicalException("More than one result found for version");
            }

            logger.debug("Database version: {}", version);
        } else {
            throw new TechnicalException("Database version not found");
        }

        return version;
    }

}
