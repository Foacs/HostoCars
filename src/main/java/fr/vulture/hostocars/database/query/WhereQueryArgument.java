package fr.vulture.hostocars.database.query;

import fr.vulture.hostocars.error.exception.TechnicalException;

/**
 * Interface exposing the behaviour of a query argument.
 */
@FunctionalInterface
public interface WhereQueryArgument {

    /**
     * Returns the conditional clause of the query argument.
     *
     * @return a conditional clause
     *
     * @throws TechnicalException
     *     if the clause fails to be generated
     */
    String clause() throws TechnicalException;

}
