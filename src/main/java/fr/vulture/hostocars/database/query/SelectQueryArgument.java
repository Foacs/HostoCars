package fr.vulture.hostocars.database.query;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Wrapper for a query {@code SELECT} clause argument.
 */
@Value(staticConstructor = "of")
@Accessors(fluent = true)
public class SelectQueryArgument {

    @NotNull
    @NotEmpty
    private final String table;

    @NotNull
    @NotEmpty
    private final String field;

}
