package fr.vulture.hostocars.database.builder;

import static java.sql.Types.LONGNVARCHAR;
import static java.sql.Types.TIMESTAMP_WITH_TIMEZONE;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Wrapper for the SQL query arguments.
 */
@Data
@AllArgsConstructor
public class QueryArgument {

    @NotNull
    @NotEmpty
    private String name;

    private Object value;

    @Min(value = LONGNVARCHAR)
    @Max(value = TIMESTAMP_WITH_TIMEZONE)
    private int type;

}
