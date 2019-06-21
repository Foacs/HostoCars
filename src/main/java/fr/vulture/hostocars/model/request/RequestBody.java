package fr.vulture.hostocars.model.request;

import java.util.Collection;

/**
 * Interface describing the behavior of a request body.
 */
public interface RequestBody {

    /**
     * Returns true if any field of the request body is not null.
     *
     * @return true if any field of the request body is not null
     */
    boolean hasNonNullFields();

    /**
     * Returns the list of query arguments from the request body.
     *
     * @return the list of query arguments
     */
    Collection<QueryArgument> getQueryArguments();

}
