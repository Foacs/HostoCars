package fr.vulture.hostocars.controller;

import static fr.vulture.hostocars.constant.SQLConstants.*;
import static fr.vulture.hostocars.model.request.QueryArgumentType.INTEGER;
import static java.util.Objects.*;
import static org.springframework.http.HttpStatus.*;

import fr.vulture.hostocars.database.DatabaseConnection;
import fr.vulture.hostocars.error.FunctionalException;
import fr.vulture.hostocars.error.TechnicalException;
import fr.vulture.hostocars.model.Contact;
import fr.vulture.hostocars.model.converter.ContactConverter;
import fr.vulture.hostocars.model.request.ContactRequestBody;
import fr.vulture.hostocars.model.request.QueryArgument;
import fr.vulture.hostocars.util.FileUtils;
import fr.vulture.hostocars.util.SQLUtils;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for the contacts.
 */
@RestController
@RequestMapping(value = "/contacts")
@CrossOrigin(origins = "*")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private DatabaseConnection connection;

    @Autowired
    private ContactConverter contactConverter;

    /**
     * Retrieves the list of all the contacts from the database.
     *
     * @return an HTTP response
     */
    @GetMapping
    public ResponseEntity<?> getContacts() {
        logger.debug("[getContacts <= Calling]");

        try {
            // Prepares the statement
            final PreparedStatement statement = connection.prepareStatement(GET_CONTACTS_QUERY);

            // If the statement is null, throws a technical exception
            if (isNull(statement)) {
                throw new TechnicalException("Failed to generate SQL statement");
            }

            // Executes the query
            final ResultSet result = statement.executeQuery();

            // If the result is null, throws a technical exception
            if (isNull(result)) {
                throw new TechnicalException("The query execution failed");
            }

            // Retrieves the resultant contacts
            final List<Contact> contacts = new ArrayList<>();
            while (result.next()) {
                // Converts the current query result to a contact entity
                final Contact contact = contactConverter.from(result);

                // If the contact entity is null, throws a technical exception
                if (isNull(contact)) {
                    throw new TechnicalException("The query returned a null element");
                }

                // Adds the contact entity to the result list
                contacts.add(contact);
            }

            // If no contact was found, returns a 204 status
            if (contacts.isEmpty()) {
                logger.debug("[getContacts => {}] No contact found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // Returns the list of found contacts with a 200 status
            logger.debug("[getContacts => {}] {} contact(s) found", OK.value(), contacts.size());
            return ResponseEntity.ok(contacts);
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (SQLException | TechnicalException e) {
            logger.error("[getContacts => {}] {}", INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Technical error: " + e.getMessage());
        }
    }

    /**
     * Retrieves the contact with the given ID from the database.
     *
     * @param id
     *     The contact's ID
     *
     * @return an HTTP response
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getContactByID(@PathVariable Integer id) {
        logger.debug("[getContactByID <= Calling] With ID = {}", id);

        try {
            // If the ID is missing, throws a functional exception
            if (isNull(id)) {
                throw new FunctionalException("Missing contact ID");
            }

            // If the ID is negative or zero, throws a functional exception
            if (MINIMUM_ID.compareTo(id) > 0) {
                throw new FunctionalException("The contact ID can not be negative or zero");
            }

            // Prepares the statement
            final PreparedStatement statement = connection.prepareStatement(GET_CONTACT_BY_ID_QUERY);

            // If the statement is null, throws a technical exception
            if (isNull(statement)) {
                throw new TechnicalException("Failed to generate SQL statement");
            }

            // Sets the query's arguments
            statement.setInt(1, id);

            // Executes the query
            final ResultSet result = statement.executeQuery();

            // If the result is null, throws a technical exception
            if (isNull(result)) {
                throw new TechnicalException("The query execution failed");
            }

            // Retrieves the resultant contact
            if (result.next()) {
                // Converts the query result to a contact entity
                final Contact contact = contactConverter.from(result);

                // If there is more than one result, throws a technical exception
                if (result.next()) {
                    throw new TechnicalException("More than one contact found for ID = {} in the database", id);
                }

                // If the found contact entity is not null, returns it with a 200 status
                if (nonNull(contact)) {
                    logger.debug("[getContactByID => {}] Contact found for ID = {}", OK.value(), id);
                    return ResponseEntity.ok(contact);
                }
            }

            // Returns a 204 status
            logger.debug("[getContactByID => {}] No contact found for ID = {}", NO_CONTENT.value(), id);
            return ResponseEntity.noContent().build();
        }

        // If a functional exception has been thrown, returns a 400 status
        catch (FunctionalException e) {
            logger.error("[getContactByID => {}] {}", BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body("Functional error: " + e.getMessage());
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (SQLException | TechnicalException e) {
            logger.error("[getContactByID => {}] {}", INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Technical error: " + e.getMessage());
        }
    }

    /**
     * Retrieves the list of contacts corresponding to filters from a JSON body in the database.
     *
     * @param body
     *     The contact request body to search
     *
     * @return an HTTP response
     */
    @PostMapping(value = "/search")
    public ResponseEntity<?> searchContacts(@RequestBody ContactRequestBody body) {
        logger.debug("[searchContacts <= Calling] With body = {}", body);

        try {
            // If the body is missing, throws a functional exception
            if (isNull(body)) {
                throw new FunctionalException("Missing contact request body");
            }

            // Prepares the statement
            final PreparedStatement statement = SQLUtils.generateStatementWithWhereClause(connection, SEARCH_CONTACTS_QUERY, body);

            // Executes the query
            final ResultSet result = statement.executeQuery();

            // If the result is null, throws a technical exception
            if (isNull(result)) {
                throw new TechnicalException("The query execution failed");
            }

            // Retrieves the resultant contacts
            final List<Contact> contacts = new ArrayList<>();
            while (result.next()) {
                // Converts the current query result to a contact entity
                final Contact contact = contactConverter.from(result);

                // If the contact entity is null, throws a technical exception
                if (isNull(contact)) {
                    throw new TechnicalException("The query returned a null element");
                }

                // Adds the contact entity to the result list
                contacts.add(contact);
            }

            // If no contact was found, returns a 204 status
            if (contacts.isEmpty()) {
                logger.debug("[searchContacts => {}] No contact found", NO_CONTENT.value());
                return ResponseEntity.noContent().build();
            }

            // Returns the list of found contacts with a 200 status
            logger.debug("[searchContacts => {}] {} contact(s) found", OK.value(), contacts.size());
            return ResponseEntity.ok(contacts);
        }

        // If a functional exception has been thrown, returns a 400 status
        catch (FunctionalException e) {
            logger.error("[searchContacts => {}] {}", BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body("Functional error: " + e.getMessage());
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (SQLException | TechnicalException e) {
            logger.error("[searchContacts => {}] {}", INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Technical error: " + e.getMessage());
        }
    }

    /**
     * Saves a new contact from a JSON body in the database.
     *
     * @param body
     *     The contact request body to save
     *
     * @return an HTTP response
     */
    @PostMapping(value = "/save")
    public ResponseEntity<?> saveContact(@RequestBody ContactRequestBody body) {
        logger.debug("[saveContact <= Calling] With body = {}", body);

        try {
            // If the body is missing, throws a functional exception
            if (isNull(body)) {
                throw new FunctionalException("Missing contact request body");
            }

            // If any of the mandatory fields are missing from the body, throws a functional exception
            if (body.hasMissingMandatoryFields()) {
                throw new FunctionalException("Missing mandatory field(s) in contact request body");
            }

            // Prepares the statement
            final PreparedStatement statement = SQLUtils.generateStatementWithInsertClause(connection, SAVE_CONTACT_QUERY, body);

            // Executes the query and retrieves the generated keys
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();

            // If the result is null, throws a technical exception
            if (isNull(result)) {
                throw new TechnicalException("The query execution failed");
            }

            // Retrieves the generated entity ID
            if (result.next()) {
                // Retrieves the generated key
                final int generatedID = result.getInt(1);

                // If the generated ID is 0, throws a technical exception
                if (MINIMUM_ID.compareTo(generatedID) > 0) {
                    throw new TechnicalException("The new contact has not been saved");
                }

                // If there is more than one generated key, throws a technical exception
                if (result.next()) {
                    throw new TechnicalException("More than one contact has been saved in the database");
                }

                // Returns the generated key with a 200 status
                logger.debug("[saveContact => {}] New contact saved with ID = {}", OK.value(), generatedID);
                return ResponseEntity.ok(generatedID);
            }

            // If the contact has not been saved, throws a technical exception
            throw new TechnicalException("The new contact has not been saved");
        }

        // If a functional exception has been thrown, returns a 400 status
        catch (FunctionalException e) {
            logger.error("[saveContact => {}] {}", BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body("Functional error: " + e.getMessage());
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (SQLException | TechnicalException e) {
            logger.error("[saveContact => {}] {}", INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Technical error: " + e.getMessage());
        }
    }

    /**
     * Updates a contact from its ID with the data from a JSON body in the database.
     *
     * @param id
     *     The ID of the contact to delete
     * @param body
     *     The data to update
     *
     * @return an HTTP response
     */
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<?> updateContactByID(@PathVariable Integer id, @RequestBody ContactRequestBody body) {
        logger.debug("[updateContactByID <= Calling] With ID = {} and body = {}", id, body);

        try {
            // If the ID is missing, throws a functional exception
            if (isNull(id)) {
                throw new FunctionalException("Missing contact ID");
            }

            // If the ID is negative or zero, throws a functional exception
            if (MINIMUM_ID.compareTo(id) > 0) {
                throw new FunctionalException("The contact ID can not be negative or zero");
            }

            // If the body is missing, throws a functional exception
            if (isNull(body)) {
                throw new FunctionalException("Missing contact request body");
            }

            // If the body has not any non null value, throws a functional exception
            if (!body.hasNonNullFields()) {
                throw new FunctionalException("No value to update");
            }

            // Prepares the statement
            final QueryArgument idArgument = new QueryArgument("id", id, INTEGER);
            final PreparedStatement statement = SQLUtils.generateStatementWithUpdateClause(connection, UPDATE_CONTACT_BY_ID_QUERY, body, idArgument);

            // Executes the query and retrieves the number of updated contacts
            int result = statement.executeUpdate();

            switch (result) {
                case 0:
                    // If no contact has been updated, returns a 204 status
                    logger.debug("[updateContactByID => {}] No contact found to update", NO_CONTENT.value());
                    return ResponseEntity.noContent().build();
                case 1:
                    // If exactly one contact has been updated, returns a 200 status
                    logger.debug("[updateContactByID => {}] Contact updated", OK.value());
                    return ResponseEntity.ok().build();
                default:
                    // If there more than one contacts have been updated, throws a technical exception
                    throw new TechnicalException("More than one contact has been updated in the database");
            }
        }

        // If a functional exception has been thrown, returns a 400 status
        catch (FunctionalException e) {
            logger.error("[updateContactByID => {}] {}", BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body("Functional error: " + e.getMessage());
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (SQLException | TechnicalException e) {
            logger.error("[updateContactByID => {}] {}", INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Technical error: " + e.getMessage());
        }
    }

    /**
     * Updates a contact's picture from its ID with the input url in the database.
     *
     * @param id
     *     The ID of the contact to delete
     * @param url
     *     The URL of the picture to add
     *
     * @return an HTTP response
     */
    @PutMapping(value = "/{id}/updatePicture")
    public ResponseEntity<?> updateContactPictureByID(@PathVariable Integer id, @RequestParam(required = false) String url) {
        logger.debug("[updateContactPictureByID <= Calling] With ID = {} and url = {}", id, url);

        try {
            // If the ID is missing, throws a functional exception
            if (isNull(id)) {
                throw new FunctionalException("Missing contact ID");
            }

            // If the ID is negative or zero, throws a functional exception
            if (MINIMUM_ID.compareTo(id) > 0) {
                throw new FunctionalException("The contact ID can not be negative or zero");
            }

            // Retrieves the picture from the url
            final byte[] picture = FileUtils.readBlobFromUrl(url);

            // Prepares the statement
            final QueryArgument idArgument = new QueryArgument("id", id, INTEGER);
            final PreparedStatement statement = SQLUtils
                .generateStatementWithUpdateClauseWithBlob(connection, UPDATE_CONTACT_PICTURE_BY_ID_QUERY, picture, idArgument);

            // Executes the query and retrieves the number of updated contacts
            int result = statement.executeUpdate();

            switch (result) {
                case 0:
                    // If no contact has been updated, returns a 204 status
                    logger.debug("[updateContactPictureByID => {}] No contact found to update", NO_CONTENT.value());
                    return ResponseEntity.noContent().build();
                case 1:
                    // If exactly one contact has been updated, returns a 200 status
                    logger.debug("[updateContactPictureByID => {}] Contact picture updated", OK.value());
                    return ResponseEntity.ok().build();
                default:
                    // If there more than one contacts have been updated, throws a technical exception
                    throw new TechnicalException("More than one contact has been updated in the database");
            }
        }

        // If a functional exception has been thrown, returns a 400 status
        catch (FunctionalException e) {
            logger.error("[updateContactPictureByID => {}] {}", BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body("Functional error: " + e.getMessage());
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (IOException | SQLException | TechnicalException e) {
            logger.error("[updateContactPictureByID => {}] {}", INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Technical error: " + e.getMessage());
        }
    }

    /**
     * Deletes a contact from its ID from the database.
     *
     * @param id
     *     The ID of the contact to delete
     *
     * @return an HTTP response
     */
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<?> deleteContactByID(@PathVariable Integer id) {
        logger.debug("[deleteContactByID <= Calling] With ID = {}", id);

        try {
            // If the ID is missing, throws a functional exception
            if (isNull(id)) {
                throw new FunctionalException("Missing contact ID");
            }

            // If the ID is negative or zero, throws a functional exception
            if (MINIMUM_ID.compareTo(id) > 0) {
                throw new FunctionalException("The contact ID can not be negative or zero");
            }

            // Prepares the statement
            final PreparedStatement statement = connection.prepareStatement(DELETE_CONTACT_BY_ID_QUERY);

            // If the statement is null, throws a technical exception
            if (isNull(statement)) {
                throw new TechnicalException("Failed to generate SQL statement");
            }

            // Sets the query's arguments
            statement.setInt(1, id);

            // Executes the query and retrieves the number of updated contacts
            int result = statement.executeUpdate();

            switch (result) {
                case 0:
                    // If no contact has been deleted, returns a 204 status
                    logger.debug("[deleteContactByID => {}] No contact found to delete", NO_CONTENT.value());
                    return ResponseEntity.noContent().build();
                case 1:
                    // If exactly one contact has been deleted, returns a 200 status
                    logger.debug("[deleteContactByID => {}] Contact deleted", OK.value());
                    return ResponseEntity.ok().build();
                default:
                    // If there more than one contacts have been deleted, throws a technical exception
                    throw new TechnicalException("More than one contact has been deleted from the database");
            }
        }

        // If a functional exception has been thrown, returns a 400 status
        catch (FunctionalException e) {
            logger.error("[deleteContactByID => {}] {}", BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body("Functional error: " + e.getMessage());
        }

        // If a technical exception has been thrown, returns a 500 status
        catch (SQLException | TechnicalException e) {
            logger.error("[deleteContactByID => {}] {}", INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Technical error: " + e.getMessage());
        }
    }

}
