package fr.vulture.hostocars.model.request.api;

import java.sql.Types;

/**
 * Container for the SQL types.
 */
public class QueryArgumentType {

    public static final int INTEGER = Types.INTEGER;
    public static final int TEXT = Types.VARCHAR;
    public static final int DATE = Types.DATE;
    public static final int BLOB = Types.BLOB;

    /**
     * Default constructor.
     */
    private QueryArgumentType() {
        super();
    }

}
