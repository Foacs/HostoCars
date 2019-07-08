package fr.vulture.hostocars.model.request.api;

import java.sql.Types;

/**
 * Container for the SQL types.
 */
public class QueryArgumentType {

    /**
     * The INTEGER SQL type.
     */
    public static final int INTEGER = Types.INTEGER;

    /**
     * The VARCHAR SQL type.
     */
    public static final int TEXT = Types.VARCHAR;

    /**
     * The DATE SQL type.
     */
    public static final int DATE = Types.DATE;

    /**
     * The BLOB SQL type.
     */
    public static final int BLOB = Types.BLOB;

    /**
     * Default constructor.
     */
    private QueryArgumentType() {
        super();
    }

    /**
     * Returns the string representation of a type.
     *
     * @param type
     *     The type
     *
     * @return a string
     */
    public static String toString(int type) {
        switch (type) {
            case INTEGER:
                return "Integer";
            case TEXT:
                return "Text";
            case DATE:
                return "Date";
            case BLOB:
                return "Blob";
            default:
                return "Unknown";
        }
    }

}
