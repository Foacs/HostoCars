package fr.vulture.hostocars.database.query;

import java.util.Locale;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Wrapper for a query {@code WHERE} clause argumentas a join.
 */
@EqualsAndHashCode(callSuper = false)
@Value(staticConstructor = "of")
@Accessors(fluent = true)
public class JoinQueryArgument implements WhereQueryArgument {

    private static final String DOT_SEPARATOR = ".";
    private static final String EQUAL_OPERATOR = " = ";

    @NotNull
    @NotEmpty
    private final String tableFrom;

    @NotNull
    @NotEmpty
    private final String columnFrom;

    @NotNull
    @NotEmpty
    private final String tableTo;

    @NotNull
    @NotEmpty
    private final String columnTo;

    @Override
    public String clause() {
        return new StringBuilder(this.tableFrom.toLowerCase(Locale.getDefault()))
            .append(DOT_SEPARATOR)
            .append(this.columnFrom)
            .append(EQUAL_OPERATOR)
            .append(this.tableTo.toLowerCase(Locale.getDefault()))
            .append(DOT_SEPARATOR)
            .append(this.columnTo)
            .toString();
    }

}
