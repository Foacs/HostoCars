package fr.vulture.hostocars.constant;

/**
 * Constant class for the SQL constants.
 */
public class SQLConstants {

    /**
     * Default constructor.
     */
    private SQLConstants() {
        super();
    }

    /* Global constants */

    /**
     * The minimum value an ID can have in the database.
     */
    public static final Integer MINIMUM_ID = 1;

    /* Queries constants */

    /**
     * Query for the getCurrentDatabaseVersion method.
     */
    public static final String GET_CURRENT_DATABASE_VERSION_QUERY = "SELECT value FROM DatabaseInfo WHERE key = 'version'";

    /**
     * Query for the getContacts method.
     */
    public static final String GET_CONTACTS_QUERY = "SELECT * FROM Contacts";

    /**
     * Query for the getContactByID method.
     */
    public static final String GET_CONTACT_BY_ID_QUERY = "SELECT * FROM Contacts WHERE id = ?";

    /**
     * Query for the searchContacts method.
     */
    public static final String SEARCH_CONTACTS_QUERY = "SELECT * FROM Contacts";

    /**
     * Query for the saveContact method.
     */
    public static final String SAVE_CONTACT_QUERY = "INSERT INTO Contacts";

    /**
     * Query for the updateContactByID method.
     */
    public static final String UPDATE_CONTACT_BY_ID_QUERY = "UPDATE Contacts SET ";

    /**
     * Query for the updateContactPictureByID method.
     */
    public static final String UPDATE_CONTACT_PICTURE_BY_ID_QUERY = "UPDATE Contacts SET picture = ";

    /**
     * Query for the deleteContactByID method.
     */
    public static final String DELETE_CONTACT_BY_ID_QUERY = "DELETE FROM Contacts WHERE id = ?";

}
