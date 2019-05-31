package fr.vulture.hostocars.controller;

import fr.vulture.hostocars.database.DatabaseConnection;
import fr.vulture.hostocars.error.TechnicalException;
import fr.vulture.hostocars.model.Contact;
import fr.vulture.hostocars.model.converter.ContactConverter;
import fr.vulture.hostocars.model.request.ContactRequestBody;
import fr.vulture.hostocars.model.request.QueryArgument;
import fr.vulture.hostocars.model.request.QueryArgumentType;
import fr.vulture.hostocars.util.SQLUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for the contacts.
 */
@RestController
@RequestMapping(value = "/contacts")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private DatabaseConnection connection;

    @Autowired
    private ContactConverter contactConverter;

    /**
     * Retrieves the list of all the contacts from the database.
     *
     * @return the list of all the contacts
     *
     * @throws SQLException
     *     if the call fails
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Contact> getContacts() throws SQLException {
        logger.debug("Calling getContacts");

        // Prepares the query
        final String query = "SELECT * FROM Contacts";

        // Prepares the statement
        final PreparedStatement statement = connection.prepareStatement(query);

        // Executes the query
        final ResultSet result = statement.executeQuery();

        // Retrieves the resultant contacts
        final List<Contact> contacts = new ArrayList<>();
        while (result.next()) {
            contacts.add(contactConverter.from(result));
        }

        if (contacts.isEmpty()) {
            logger.info("No contact found in the database");
        } else {
            logger.info("{} contacts found in the database", contacts.size());
        }

        return contacts;
    }

    /**
     * Retrieves the contact with the given ID from the database.
     *
     * @return the contact with the given ID
     *
     * @throws SQLException
     *     if the call fails
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Contact getContactByID(@PathVariable Integer id) throws SQLException, TechnicalException {
        logger.debug("Calling getContactByID with ID = {}", id);

        // Prepares the statement
        final String query = "SELECT * FROM Contacts WHERE id = ?";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        // Executes the query
        final ResultSet result = statement.executeQuery();

        // Retrieves the resultant contact
        Contact contact = null;
        if (result.next()) {
            contact = contactConverter.from(result);

            if (result.next()) {
                // If there is more than one result, throw a technical exception
                throw new TechnicalException("More than one contact found for ID = {0} in the database", id);
            }

            logger.info("Contact found for ID: {}", id);
        } else {
            logger.info("No contact found for ID: {}", id);
        }

        return contact;
    }

    /**
     * Retrieves the list of contacts corresponding to filters from a JSON request in the database.
     *
     * @param contact
     *     The contact request body to search
     *
     * @throws SQLException
     *     if the call fails
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<Contact> searchContacts(@RequestBody ContactRequestBody contact) throws SQLException {
        logger.debug("Calling searchContacts with request body = {}", contact);

        // Prepares the statement
        final String query = "SELECT * FROM Contacts";
        final PreparedStatement statement = SQLUtils.generateStatementWithWhereClause(connection, query, contact);

        // Executes the query
        final ResultSet result = statement.executeQuery();

        // Retrieves the resultant contacts
        final List<Contact> contacts = new ArrayList<>();
        while (result.next()) {
            contacts.add(contactConverter.from(result));
        }

        if (contacts.isEmpty()) {
            logger.info("No contact found in the database for search request: {}", contact);
        } else {
            logger.info("{} contacts found in the database for search request: {}", contacts.size(), contact);
        }

        return contacts;
    }

    /**
     * Saves a new contact from a JSON request in the database.
     *
     * @param contact
     *     The contact request body to save
     *
     * @throws SQLException
     *     if the call fails
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveContact(@RequestBody ContactRequestBody contact) throws SQLException {
        logger.debug("Calling saveContact with request body = {}", contact);

        // Prepares the statement
        final String query = "INSERT INTO Contacts";
        final PreparedStatement statement = SQLUtils.generateStatementWithInsertClause(connection, query, contact);

        // Executes the query
        statement.executeUpdate();
        logger.info("New contact saved");
    }

    /**
     * Updates a contact from its ID with the data from a JSON request in the database.
     *
     * @param id
     *     The ID of the contact to delete
     * @param contact
     *     The data to update
     *
     * @throws SQLException
     *     if the call fails
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.PUT)
    public void updateContact(@PathVariable Integer id, @RequestBody ContactRequestBody contact) throws SQLException {
        logger.debug("Calling updateContact with id = {} and request body = {}", id, contact);

        if (contact.hasNonNullFields()) {
            // Prepares the statement
            final String query = "UPDATE Contacts SET ";
            final QueryArgument idArgument = new QueryArgument("id", id, QueryArgumentType.INTEGER);
            final PreparedStatement statement = SQLUtils.generateStatementWithUpdateClause(connection, query, contact, idArgument);

            // Executes the query
            if (statement.executeUpdate() == 0) {
                logger.info("Contact not found for ID: {}, nothing to update", id);
            } else {
                logger.info("Contact {} updated", id);
            }
        } else {
            logger.warn("No field to update");
        }
    }

    /**
     * Deletes a contact from its ID from the database.
     *
     * @param id
     *     The ID of the contact to delete
     *
     * @throws SQLException
     *     if the call fails
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    public void deleteContact(@PathVariable Integer id) throws SQLException {
        logger.debug("Calling deleteContact with id = {}", id);

        // Prepares the statement
        final String query = "DELETE FROM Contacts WHERE id = ?";
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        // Executes the query
        if (statement.executeUpdate() == 0) {
            logger.info("Contact not found for ID: {}, nothing to delete", id);
        } else {
            logger.info("Contact {} deleted", id);
        }
    }

}
