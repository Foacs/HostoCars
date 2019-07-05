package fr.vulture.hostocars.model.request.api;

import java.util.Collection;

/**
 * Interface describing the behavior of a search request body.
 */
public interface SearchRequestBody {

    /**
     * Returns the list of query arguments from the search request body.
     *
     * @return the list of search query arguments
     */
    Collection<QueryArgument> getSearchQueryArguments();

    /**
     * Returns true if any field of the search request body is not null.
     *
     * @return true if any field of the search request body is not null
     */
    boolean hasNonNullSearchFields();

}
