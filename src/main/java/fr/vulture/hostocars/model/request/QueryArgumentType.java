package fr.vulture.hostocars.model.request;

import java.sql.Types;

/**
 * Container for the SQL types.
 */
public class QueryArgumentType {

    public static final int BOOLEAN = Types.BOOLEAN;
    public static final int INTEGER = Types.INTEGER;
    public static final int TEXT = Types.VARCHAR;

    /**
     * Default constructor.
     */
    private QueryArgumentType() {
        super();
    }

}
