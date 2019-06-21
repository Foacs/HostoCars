package fr.vulture.hostocars.util;

import static java.util.Objects.*;

import fr.vulture.hostocars.database.DatabaseConnection;
import fr.vulture.hostocars.error.TechnicalException;
import fr.vulture.hostocars.model.request.QueryArgument;
import fr.vulture.hostocars.model.request.RequestBody;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Utility class to manipulate SQL queries.
 */
public class SQLUtils {

    /**
     * Default constructor.
     */
    private SQLUtils() {
        super();
    }

    /**
     * Generates a prepared statement from a basic query and a request body with a 'WHERE' clause.
     *
     * @param connection
     *     The connection used to generate the statement
     * @param query
     *     The basic query
     * @param requestBody
     *     The request body
     *
     * @return a prepared statement with a 'WHERE' clause
     *
     * @throws SQLException
     *     if the statement generation failed
     */
    public static PreparedStatement generateStatementWithWhereClause(final DatabaseConnection connection, final String query,
        final RequestBody requestBody) throws SQLException, TechnicalException {
        // Gets the query arguments from the request body
        final Iterable<QueryArgument> queryArguments = requestBody.getQueryArguments();

        // If the request body has non null fields, adds a 'WHERE' clause
        if (requestBody.hasNonNullFields()) {
            final StringBuilder queryBuilder = new StringBuilder(query);
            final Iterator<QueryArgument> iterator = queryArguments.iterator();

            // Adds the first query argument with a 'WHERE' clause
            final QueryArgument firstArgument = iterator.next();
            queryBuilder.append(" WHERE ").append(firstArgument.getName());

            if (nonNull(firstArgument.getValue())) {
                queryBuilder.append(" = ?");
            } else {
                queryBuilder.append(" IS NULL");
            }

            // Adds the other query arguments with 'AND' clauses
            while (iterator.hasNext()) {
                final QueryArgument argument = iterator.next();
                queryBuilder.append(" AND ").append(argument.getName());

                if (nonNull(argument.getValue())) {
                    queryBuilder.append(" = ?");
                } else {
                    queryBuilder.append(" IS NULL");
                }
            }

            // Generates the statement
            final PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());

            // If the statement is null, throws a technical exception
            if (isNull(statement)) {
                throw new TechnicalException("Failed to generate SQL statement");
            }

            // Sets the query arguments to the statement
            int index = 1;
            for (final QueryArgument argument : queryArguments) {
                statement.setObject(index++, argument.getValue(), argument.getType());
            }

            return statement;
        }

        // Else, just returns a statement from the basic query
        return connection.prepareStatement(query);
    }

    /**
     * Generates a prepared statement from a basic query and a request body for an 'INSERT' clause.
     *
     * @param connection
     *     The connection used to generate the statement
     * @param query
     *     The basic query
     * @param requestBody
     *     The request body
     *
     * @return a prepared statement with an 'INSERT' clause
     *
     * @throws SQLException
     *     if the statement generation failed
     */
    public static PreparedStatement generateStatementWithInsertClause(final DatabaseConnection connection, final String query,
        final RequestBody requestBody) throws SQLException, TechnicalException {
        // Gets the query arguments from the request body
        final Iterable<QueryArgument> queryArguments = requestBody.getQueryArguments();

        final StringBuilder queryBuilder = new StringBuilder(query);

        // If the request body has non null fields, adds a 'VALUES' clause
        if (requestBody.hasNonNullFields()) {
            queryBuilder.append("(");
            final StringBuilder queryValuesBuilder = new StringBuilder(" VALUES (");

            final Iterator<QueryArgument> iterator = queryArguments.iterator();

            // Adds the first query argument
            final QueryArgument firstArgument = iterator.next();
            queryBuilder.append(firstArgument.getName());
            queryValuesBuilder.append("?");

            // Adds the other query arguments
            while (iterator.hasNext()) {
                final QueryArgument argument = iterator.next();
                queryBuilder.append(", ").append(argument.getName());
                queryValuesBuilder.append(", ?");
            }

            // Adds the closing parenthesis and the 'VALUES' clause
            queryBuilder.append(")").append(queryValuesBuilder.append(")"));

            // Generates the statement
            final PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());

            // If the statement is null, throws a technical exception
            if (isNull(statement)) {
                throw new TechnicalException("Failed to generate SQL statement");
            }

            // Sets the query arguments to the statement
            int index = 1;
            for (final QueryArgument argument : queryArguments) {
                statement.setObject(index++, argument.getValue(), argument.getType());
            }

            return statement;
        }

        // Else, just returns a statement from the basic query with default values
        return connection.prepareStatementWithGeneratedKeys(queryBuilder.append(" DEFAULT VALUES").toString());
    }

    /**
     * Generates a prepared statement from a basic query and a request body for an 'UPDATE' clause.
     *
     * @param connection
     *     The connection used to generate the statement
     * @param query
     *     The basic query
     * @param requestBody
     *     The request body
     * @param id
     *     The ID of the entity to update
     *
     * @return a prepared statement with an 'UPDATE' clause
     *
     * @throws SQLException
     *     if the statement generation failed
     */
    public static PreparedStatement generateStatementWithUpdateClause(final DatabaseConnection connection, final String query,
        final RequestBody requestBody, final QueryArgument id) throws SQLException, TechnicalException {
        // Gets the query arguments from the request body
        final Iterable<QueryArgument> queryArguments = requestBody.getQueryArguments();

        final StringBuilder queryBuilder = new StringBuilder(query);

        // If the request body has non null fields, adds a 'VALUES' clause
        if (requestBody.hasNonNullFields()) {
            final Iterator<QueryArgument> iterator = queryArguments.iterator();

            // Adds the first query argument
            final QueryArgument firstArgument = iterator.next();
            queryBuilder.append(firstArgument.getName()).append(" = ?");

            // Adds the other query arguments
            while (iterator.hasNext()) {
                final QueryArgument argument = iterator.next();
                queryBuilder.append(", ").append(argument.getName()).append(" = ?");
            }

            // Adds the closing 'WHERE' clause for the ID
            queryBuilder.append(" WHERE ").append(id.getName()).append(" = ?");

            // Generates the statement
            final PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());

            // If the statement is null, throws a technical exception
            if (isNull(statement)) {
                throw new TechnicalException("Failed to generate SQL statement");
            }

            // Sets the query arguments to the statement
            int index = 1;
            for (final QueryArgument argument : queryArguments) {
                statement.setObject(index++, argument.getValue(), argument.getType());
            }

            // Sets the ID argument
            statement.setObject(index, id.getValue(), id.getType());

            return statement;
        }

        // Else, just returns a statement from the basic query with default values
        return connection.prepareStatement(queryBuilder.append(" DEFAULT VALUES").toString());
    }

    /**
     * Generates a prepared statement from a basic query and a BLOB for an 'UPDATE' clause.
     *
     * @param connection
     *     The connection used to generate the statement
     * @param query
     *     The basic query
     * @param blob
     *     The BLOB
     * @param id
     *     The ID of the entity to update
     *
     * @return a prepared statement with an 'UPDATE' clause for a BLOB
     *
     * @throws SQLException
     *     if the statement generation failed
     */
    public static PreparedStatement generateStatementWithUpdateClauseWithBlob(final DatabaseConnection connection, final String query,
        final byte[] blob, final QueryArgument id) throws SQLException {
        final StringBuilder queryBuilder = new StringBuilder(query);

        final PreparedStatement statement;

        // If the URL is null, sets the picture to NULL
        if (isNull(blob)) {
            // Sets the picture to NULL <and adds the closing 'WHERE' clause for the ID
            queryBuilder.append("NULL WHERE ").append(id.getName()).append(" = ?");

            // Generates the statement
            statement = connection.prepareStatement(queryBuilder.toString());

            // Sets the ID argument
            statement.setObject(1, id.getValue(), id.getType());
        } else {
            // Adds the picture argument and the closing 'WHERE' clause for the ID
            queryBuilder.append("? WHERE ").append(id.getName()).append(" = ?");

            // Generates the statement
            statement = connection.prepareStatement(queryBuilder.toString());

            // Sets the picture argument
            statement.setBytes(1, blob);

            // Sets the ID argument
            statement.setObject(2, id.getValue(), id.getType());
        }

        return statement;
    }

}
