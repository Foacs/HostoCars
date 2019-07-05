package fr.vulture.hostocars.model.request.api;

import java.util.Collection;

/**
 * Interface describing the behavior of an update request body.
 */
public interface UpdateRequestBody {

    /**
     * Returns the list of query arguments from the update request body.
     *
     * @return the list of update query arguments
     */
    Collection<QueryArgument> getUpdateQueryArguments();

    /**
     * Returns true if any field of the update request body is not null.
     *
     * @return true if any field of the update request body is not null
     */
    boolean hasNonNullUpdateFields();

    /**
     * Returns true if the mandatory fields are not null.
     *
     * @return true if the mandatory fields are not null
     */
    boolean hasMissingMandatoryFields();

}
