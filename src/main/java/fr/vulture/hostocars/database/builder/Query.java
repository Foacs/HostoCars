package fr.vulture.hostocars.database.builder;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Wrapper for an SQL query and its arguments.
 */
@Data
public class Query {

    @NotNull
    @NotEmpty
    private String query;

    @NotNull
    private List<QueryArgument> arguments = new ArrayList<>();

}
