package fr.vulture.hostocars.controller;

import fr.vulture.hostocars.database.DatabaseConnection;
import fr.vulture.hostocars.model.Contact;
import fr.vulture.hostocars.model.converter.ContactConverter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for the contacts.
 */
@RestController
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private DatabaseConnection connection;

    @Autowired
    private ContactConverter contactConverter;

    /**
     * Returns the whole list of contacts.
     *
     * @return the list of contacts
     */
    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public List<Contact> getContacts() {
        logger.debug("Calling getContacts");

        // Prepares the query
        final String query = "SELECT * FROM Contacts";

        List<Contact> contacts = null;
        try {
            // Prepares the statement
            final PreparedStatement statement = connection.prepareStatement(query);
            // Executes the query
            final ResultSet result = statement.executeQuery();
            // Retrieves the resultant contacts
            contacts = new ArrayList<>();
            while (result.next()) {
                final Contact contact = contactConverter.from(result);
                contacts.add(contact);
                logger.debug(" > {}", contact);
            }

            logger.debug("Call to getContacts ended successfully ({} results)", contacts.size());
        } catch (SQLException e) {
            logger.error("Error while calling getContacts", e);
        }

        return contacts;
    }

    /**
     * Returns the list of contacts with the given name.
     *
     * @param name
     *     The name to search
     *
     * @return the resultant list of contacts
     */
    @RequestMapping(value = "/contacts/{name}", method = RequestMethod.GET)
    public List<Contact> getContactsByName(@PathVariable String name) {
        logger.debug("Calling getContactsByName with name=\"{}\"", name);

        // Prepares the query
        final String query = "SELECT * FROM Contacts WHERE name = (?)";

        List<Contact> contacts = null;
        try {
            // Prepares the statement
            final PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            // Executes the query
            final ResultSet result = statement.executeQuery();
            // Retrieves the resultant contacts
            contacts = new ArrayList<>();
            while (result.next()) {
                final Contact contact = contactConverter.from(result);
                contacts.add(contact);
                logger.debug(" > {}", contact);
            }

            logger.debug("Call to getContactsByName ended successfully ({} results)", contacts.size());
        } catch (SQLException e) {
            logger.error("Error while calling getContactsByName with name=\"{}\"", name, e);
        }

        return contacts;
    }

    /**
     * Inserts a new contact in the database with the given name.
     *
     * @param name
     *     The name of the new contact
     */
    @RequestMapping(value = "/contacts/{name}/add", method = RequestMethod.PUT)
    public void insertContact(@PathVariable String name) {
        logger.debug("Calling insertContact with name=\"{}\"", name);

        // Prepares the query
        String query = "INSERT INTO Contacts(name) VALUES(?)";

        try {
            // Prepares the statement
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            // Executes the query
            statement.executeUpdate();

            logger.info("Contact inserted into database with name \"{}\"", name);

            logger.debug("Call to insertContact ended successfully");
        } catch (SQLException e) {
            logger.error("Error while calling insertContact with name=\"{}\"", name, e);
        }
    }

}
