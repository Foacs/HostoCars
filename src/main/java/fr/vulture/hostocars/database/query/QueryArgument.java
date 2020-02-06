package fr.vulture.hostocars.database.query;

import static java.sql.Types.LONGNVARCHAR;
import static java.sql.Types.TIMESTAMP_WITH_TIMEZONE;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Wrapper for a query {@code INSERT} clause argument.
 */
@Data
@Accessors(fluent = true)
public class QueryArgument {

    @NotNull
    @NotEmpty
    final String column;
    final Object value;
    @Min(LONGNVARCHAR)
    @Max(TIMESTAMP_WITH_TIMEZONE)
    final int type;

    /**
     * Valued constructor.
     *
     * @param column
     *     The column
     * @param value
     *     The value
     * @param type
     *     The value type
     */
    QueryArgument(final String column, final Object value, final int type) {
        this.column = column;
        this.value = value;
        this.type = type;
    }

    /**
     * Static valued constructor.
     *
     * @param column
     *     The column
     * @param value
     *     The value
     * @param type
     *     The value type
     *
     * @return an instance of {@link QueryArgument}
     */
    public static QueryArgument of(final String column, final Object value, final int type) {
        return new QueryArgument(column, value, type);
    }

}
