package fr.vulture.hostocars.model.request.api;

import lombok.Data;
import org.slf4j.helpers.MessageFormatter;

/**
 * Wrapper for the SQL query arguments.
 */
@Data
public class QueryArgument {

    private final String name;
    private final Object value;
    private final int type;

    @Override
    public String toString() {
        Object[] arguments = new Object[]{
            name,
            value,
            QueryArgumentType.toString(type)
        };

        return MessageFormatter.arrayFormat("QueryArgument { Name: {} | Value: {} | Type: {} }", arguments).getMessage();
    }

}
