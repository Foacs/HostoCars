package fr.vulture.hostocars.database;

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

    private static final String GET_CURRENT_DATABASE_VERSION_QUERY = "SELECT value FROM DatabaseInfo WHERE key = 'version'";

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
    String getCurrentDatabaseVersion() throws SQLException, TechnicalException {
        logger.debug("Retrieving the current database version");

        // Prepares the statement
        final PreparedStatement statement = connection.prepareStatement(GET_CURRENT_DATABASE_VERSION_QUERY);

        // Executes the query
        final ResultSet result = statement.executeQuery();

        // Retrieves the version
        if (result.next()) {
            final String version = result.getString("value");

            logger.debug("The current database version ({}) has been successfully retrieved", version);
            return version;
        }

        throw new TechnicalException("Database version not found");
    }

}
