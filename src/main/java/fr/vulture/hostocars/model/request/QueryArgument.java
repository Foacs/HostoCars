package fr.vulture.hostocars.model.request;

import lombok.Data;

/**
 * Wrapper for the SQL query arguments.
 */
@Data
public class QueryArgument {

    private final String name;
    private final Object value;
    private final int type;

}
