package fr.vulture.hostocars.database.query;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Wrapper for a query {@code ORDER BY} clause argument.
 */
@Value(staticConstructor = "of")
@Accessors(fluent = true)
public class OrderByQueryArgument {

    /**
     * The {@code ASC} clause of an {@code ORDER BY} SQL clause.
     */
    public static final String ASC = "ASC";

    /**
     * The {@code DESC} clause of an {@code ORDER BY} SQL clause.
     */
    public static final String DESC = "DESC";

    @NotNull
    @NotEmpty
    private final String table;

    @NotNull
    @NotEmpty
    private final String column;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "ASC|DESC")
    private final String order;

}
