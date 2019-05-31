package fr.vulture.hostocars.util;

import fr.vulture.hostocars.database.DatabaseConnection;
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
    public static PreparedStatement generateStatementWithWhereClause(DatabaseConnection connection, String query, RequestBody requestBody)
        throws SQLException {
        // Gets the query arguments from the request body
        Iterable<QueryArgument> queryArguments = requestBody.getQueryArguments();

        // If the request body has non null fields, adds a 'WHERE' clause
        if (requestBody.hasNonNullFields()) {
            final StringBuilder queryBuilder = new StringBuilder(query);
            Iterator<QueryArgument> iterator = queryArguments.iterator();

            // Adds the first query argument with a 'WHERE' clause
            QueryArgument firstArgument = iterator.next();
            queryBuilder.append(" WHERE ").append(firstArgument.getName());

            if (firstArgument.getValue() != null) {
                queryBuilder.append(" = ?");
            } else {
                queryBuilder.append(" IS NULL");
            }

            // Adds the other query arguments with 'AND' clauses
            while (iterator.hasNext()) {
                QueryArgument argument = iterator.next();
                queryBuilder.append(" AND ").append(argument.getName());

                if (argument.getValue() != null) {
                    queryBuilder.append(" = ?");
                } else {
                    queryBuilder.append(" IS NULL");
                }
            }

            // Generates the statement
            PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());

            // Sets the query arguments to the statement
            int index = 1;
            for (QueryArgument argument : queryArguments) {
                if (argument.getValue() != null) {
                    statement.setObject(index++, argument.getValue(), argument.getType());
                }
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
    public static PreparedStatement generateStatementWithInsertClause(DatabaseConnection connection, String query, RequestBody requestBody)
        throws SQLException {
        // Gets the query arguments from the request body
        Iterable<QueryArgument> queryArguments = requestBody.getQueryArguments();

        final StringBuilder queryBuilder = new StringBuilder(query);

        // If the request body has non null fields, adds a 'VALUES' clause
        if (requestBody.hasNonNullFields()) {
            queryBuilder.append("(");
            final StringBuilder queryValuesBuilder = new StringBuilder(" VALUES (");

            Iterator<QueryArgument> iterator = queryArguments.iterator();

            // Adds the first query argument
            QueryArgument firstArgument = iterator.next();
            queryBuilder.append(firstArgument.getName());
            queryValuesBuilder.append("?");

            // Adds the other query arguments
            while (iterator.hasNext()) {
                QueryArgument argument = iterator.next();
                queryBuilder.append(", ").append(argument.getName());
                queryValuesBuilder.append(", ?");
            }

            // Adds the closing parenthesis and the 'VALUES' clause
            queryBuilder.append(")").append(queryValuesBuilder.append(")"));

            // Generates the statement
            PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());

            // Sets the query arguments to the statement
            int index = 1;
            for (QueryArgument argument : queryArguments) {
                if (argument.getValue() != null) {
                    statement.setObject(index++, argument.getValue(), argument.getType());
                }
            }

            return statement;
        }

        // Else, just returns a statement from the basic query with default values
        return connection.prepareStatement(queryBuilder.append(" DEFAULT VALUES").toString());
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
    public static PreparedStatement generateStatementWithUpdateClause(DatabaseConnection connection, String query, RequestBody requestBody, QueryArgument id)
        throws SQLException {
        // Gets the query arguments from the request body
        Iterable<QueryArgument> queryArguments = requestBody.getQueryArguments();

        final StringBuilder queryBuilder = new StringBuilder(query);

        // If the request body has non null fields, adds a 'VALUES' clause
        if (requestBody.hasNonNullFields()) {
            Iterator<QueryArgument> iterator = queryArguments.iterator();

            // Adds the first query argument
            QueryArgument firstArgument = iterator.next();
            queryBuilder.append(firstArgument.getName()).append(" = ?");

            // Adds the other query arguments
            while (iterator.hasNext()) {
                QueryArgument argument = iterator.next();
                queryBuilder.append(", ").append(argument.getName()).append(" = ?");
            }

            // Adds the closing 'WHERE' clause for the ID
            queryBuilder.append(" WHERE ").append(id.getName()).append(" = ?");

            // Generates the statement
            PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());

            // Sets the query arguments to the statement
            int index = 1;
            for (QueryArgument argument : queryArguments) {
                if (argument.getValue() != null) {
                    statement.setObject(index++, argument.getValue(), argument.getType());
                }
            }

            // Sets the ID argument
            statement.setObject(index, id.getValue(), id.getType());

            return statement;
        }

        // Else, just returns a statement from the basic query with default values
        return connection.prepareStatement(queryBuilder.append(" DEFAULT VALUES").toString());
    }

}
