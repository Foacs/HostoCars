package fr.vulture.hostocars.database.query;

import static java.sql.Types.BLOB;
import static java.sql.Types.DATE;
import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;
import static java.util.Objects.nonNull;

import fr.vulture.hostocars.error.exception.TechnicalException;
import java.sql.Types;
import java.util.Locale;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Wrapper for a query {@code WHERE} clause argument as a filter.
 */
@EqualsAndHashCode(callSuper = false)
@Value
@Accessors(fluent = true)
public class FilterQueryArgument extends QueryArgument implements WhereQueryArgument {

    private static final String EQUAL_OPERATOR = " = ";
    private static final String LIKE_OPERATOR = " LIKE ";
    private static final String IS_OPERATOR = " IS ";
    private static final String DOT_SEPARATOR = ".";
    private static final String PARAMETER_SYMBOL = "?";
    @NotNull
    @NotEmpty
    private final String table;

    /**
     * Valued constructor.
     *
     * @param table
     *     The table
     * @param column
     *     The column
     * @param value
     *     The value
     * @param type
     *     The value type
     */
    private FilterQueryArgument(final String table, final String column, final Object value, final int type) {
        super(column, value, type);
        this.table = table;
    }

    /**
     * Static valued constructor.
     *
     * @param table
     *     The table
     * @param column
     *     The column
     * @param value
     *     The value
     * @param type
     *     The value type
     *
     * @return an instance of {@link FilterQueryArgument}
     */
    public static FilterQueryArgument of(final String table, final String column, final Object value, final int type) {
        return new FilterQueryArgument(table, column, value, type);
    }

    @Override
    public String clause() throws TechnicalException {
        return new StringBuilder(this.table.toLowerCase(Locale.getDefault()))
            .append(DOT_SEPARATOR)
            .append(this.column)
            .append(getWhereClauseOperator(this.type, this.value))
            .append(PARAMETER_SYMBOL)
            .toString();
    }

    /**
     * Returns the operator that corresponds to the argument given its type (see {@link Types}) and value for a {@code WHERE} clause.
     *
     * @param type
     *     The argument type
     * @param value
     *     The argument value
     *
     * @return an SQL operator
     *
     * @throws TechnicalException
     *     if the argument type is prohibited or unknown
     */
    private static String getWhereClauseOperator(final int type, final Object value) throws TechnicalException {
        switch (type) {
            case INTEGER:
            case DATE:
                return nonNull(value) ? EQUAL_OPERATOR : IS_OPERATOR;
            case VARCHAR:
                return nonNull(value) ? LIKE_OPERATOR : IS_OPERATOR;
            case BLOB:
                throw new TechnicalException("Search over a BLOB element is prohibited");
            default:
                throw new TechnicalException("Unknown query argument type");
        }
    }

}
