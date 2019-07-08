package fr.vulture.hostocars.util;

import static java.util.Objects.*;

import fr.vulture.hostocars.database.DatabaseConnection;
import fr.vulture.hostocars.error.FunctionalException;
import fr.vulture.hostocars.error.TechnicalException;
import fr.vulture.hostocars.model.request.api.QueryArgument;
import fr.vulture.hostocars.model.request.api.QueryArgumentType;
import fr.vulture.hostocars.model.request.api.SearchRequestBody;
import fr.vulture.hostocars.model.request.api.UpdateRequestBody;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Utility class to manipulate SQL queries.
 */
public class SQLUtils {

    /**
     * The minimum value an ID can have in the database.
     */
    public static final Integer MINIMUM_ID = 1;

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
     * @throws TechnicalException
     *     if a technical error occurs
     * @throws FunctionalException
     *     if a functional error occurs
     */
    public static PreparedStatement generateStatementWithWhereClause(final DatabaseConnection connection, final String query,
        final SearchRequestBody requestBody) throws SQLException, TechnicalException, FunctionalException {
        // If the initial query is missing, throws a technical exception
        if (isNull(query)) {
            throw new TechnicalException("Missing initial query");
        }

        // If the body is missing, throws a functional exception
        if (isNull(requestBody)) {
            throw new FunctionalException("Missing request body");
        }

        // Gets the query arguments from the request body
        final Iterable<QueryArgument> queryArguments = requestBody.getSearchQueryArguments();

        // If the request body has non null fields, builds the query
        if (requestBody.hasNonNullSearchFields()) {
            final StringBuilder queryBuilder = new StringBuilder(query);

            // Adds the 'WHERE' clause
            queryBuilder.append(" WHERE ");

            // Adds the query arguments
            final Iterator<QueryArgument> iterator = queryArguments.iterator();
            while (iterator.hasNext()) {
                final QueryArgument argument = iterator.next();

                // Adds the current query argument name
                queryBuilder.append(argument.getName());

                if (nonNull(argument.getValue())) {
                    // If the current query argument's value is not null, adds the corresponding operator
                    queryBuilder.append(getArgumentTypeSearchOperator(argument.getType()));
                } else {
                    // Else, adds an 'IS NULL' clause
                    queryBuilder.append(" IS NULL");
                }

                // If there are more arguments, adds an 'AND' clause
                if (iterator.hasNext()) {
                    queryBuilder.append(" AND ");
                }
            }

            // Generates the statement
            final PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());

            // If the statement is null, throws a technical exception
            if (isNull(statement)) {
                throw new TechnicalException("Failed to generate the SQL statement");
            }

            // Sets the query arguments to the statement
            int index = 1;
            for (final QueryArgument argument : queryArguments) {
                // If the current query argument's value in not null, sets its value in the query and increments the index
                if (nonNull(argument.getValue())) {
                    statement.setObject(index++, getArgumentTypeSearchValue(argument), argument.getType());
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
     * @throws IOException
     *     *     if a file fails to be read
     * @throws SQLException
     *     if the statement generation failed
     * @throws TechnicalException
     *     if a technical error occurs
     * @throws FunctionalException
     *     if a functional error occurs
     */
    public static PreparedStatement generateStatementWithInsertClause(final DatabaseConnection connection, final String query,
        final UpdateRequestBody requestBody) throws IOException, SQLException, TechnicalException, FunctionalException {
        // If the initial query is missing, throws a technical exception
        if (isNull(query)) {
            throw new TechnicalException("Missing initial query");
        }

        // If the body is missing, throws a functional exception
        if (isNull(requestBody)) {
            throw new FunctionalException("Missing request body");
        }

        // If any of the mandatory fields are missing from the body, throws a functional exception
        if (requestBody.hasMissingMandatoryFields()) {
            throw new FunctionalException("Missing mandatory field(s) in request body");
        }

        // Gets the query arguments from the request body
        final Iterable<QueryArgument> queryArguments = requestBody.getUpdateQueryArguments();

        final StringBuilder queryBuilder = new StringBuilder(query);

        // If the request body has non null fields, adds a 'VALUES' clause
        if (requestBody.hasNonNullUpdateFields()) {
            queryBuilder.append(" (");
            final StringBuilder queryValuesBuilder = new StringBuilder(" VALUES (");

            // Adds the query arguments
            final Iterator<QueryArgument> iterator = queryArguments.iterator();
            while (iterator.hasNext()) {
                final QueryArgument argument = iterator.next();
                queryBuilder.append(argument.getName());
                queryValuesBuilder.append("?");

                if (iterator.hasNext()) {
                    queryBuilder.append(", ");
                    queryValuesBuilder.append(", ");
                }
            }

            // Adds the closing parenthesis and the 'VALUES' clause
            queryBuilder.append(")").append(queryValuesBuilder).append(")");

            // Generates the statement
            final PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());

            // If the statement is null, throws a technical exception
            if (isNull(statement)) {
                throw new TechnicalException("Failed to generate the SQL statement");
            }

            // Sets the query arguments to the statement
            int index = 1;
            for (final QueryArgument argument : queryArguments) {
                if (argument.getType() == QueryArgumentType.BLOB) {
                    // If the argument is a BLOB, reads the file from the URL
                    final byte[] blob = FileUtils.readBlobFromUrl((String) argument.getValue());

                    if (isNull(blob)) {
                        // If the read BLOB is null, sets the value to NULL
                        statement.setNull(index++, QueryArgumentType.BLOB);
                    } else {
                        // Else, sets the BLOB
                        statement.setBytes(index++, blob);
                    }
                } else {
                    statement.setObject(index++, argument.getValue(), argument.getType());
                }
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
     * @throws IOException
     *     if a file fails to be read
     * @throws SQLException
     *     if the statement generation failed
     * @throws TechnicalException
     *     if a technical error occurs
     * @throws FunctionalException
     *     if a functional error occurs
     */
    public static PreparedStatement generateStatementWithUpdateClause(final DatabaseConnection connection, final String query,
        final UpdateRequestBody requestBody, final QueryArgument id) throws IOException, SQLException, TechnicalException, FunctionalException {
        // If the initial query is missing, throws a technical exception
        if (isNull(query)) {
            throw new TechnicalException("Missing initial query");
        }

        // If the body is missing, throws a functional exception
        if (isNull(requestBody)) {
            throw new FunctionalException("Missing request body");
        }

        // If the body has not any non null value, throws a functional exception
        if (!requestBody.hasNonNullUpdateFields()) {
            throw new FunctionalException("No value to update");
        }

        // Gets the query arguments from the request body
        final Iterable<QueryArgument> queryArguments = requestBody.getUpdateQueryArguments();

        final StringBuilder queryBuilder = new StringBuilder(query);

        // If the request body has non null fields, adds a 'VALUES' clause
        if (requestBody.hasNonNullUpdateFields()) {

            // Adds the query arguments
            final Iterator<QueryArgument> iterator = queryArguments.iterator();
            while (iterator.hasNext()) {
                final QueryArgument argument = iterator.next();
                queryBuilder.append(argument.getName()).append(" = ?");

                if (iterator.hasNext()) {
                    queryBuilder.append(", ");
                }
            }

            // Adds the closing 'WHERE' clause for the ID
            queryBuilder.append(" WHERE ").append(id.getName()).append(" = ?");

            // Generates the statement
            final PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());

            // If the statement is null, throws a technical exception
            if (isNull(statement)) {
                throw new TechnicalException("Failed to generate the SQL statement");
            }

            // Sets the query arguments to the statement
            int index = 1;
            for (final QueryArgument argument : queryArguments) {
                if (argument.getType() == QueryArgumentType.BLOB) {
                    // If the argument is a BLOB, reads the file from the URL
                    final byte[] blob = FileUtils.readBlobFromUrl((String) argument.getValue());

                    if (isNull(blob)) {
                        // If the read BLOB is null, sets the value to NULL
                        statement.setNull(index++, argument.getType());
                    } else {
                        // Else, sets the BLOB
                        statement.setBytes(index++, blob);
                    }
                } else {
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

    /**
     * Returns the operator corresponding to the given query argument type.
     *
     * @param argumentType
     *     The query argument type
     *
     * @return an SQL operator
     *
     * @throws TechnicalException
     *     if the query argument type is prohibited or unknown
     */
    private static String getArgumentTypeSearchOperator(int argumentType) throws TechnicalException {
        switch (argumentType) {
            case QueryArgumentType.INTEGER:
            case QueryArgumentType.TEXT:
                return " LIKE ?";
            case QueryArgumentType.DATE:
                return " = ?";
            case QueryArgumentType.BLOB:
                throw new TechnicalException("Search over a BLOB element is prohibited");
            default:
                throw new TechnicalException("Unknown query argument type");
        }
    }

    /**
     * Returns the value corresponding to the given query argument's type as a string.
     *
     * @param argument
     *     The query argument
     *
     * @return a value as a string
     *
     * @throws TechnicalException
     *     if the query argument type is prohibited or unknown
     */
    private static String getArgumentTypeSearchValue(QueryArgument argument) throws TechnicalException {
        switch (argument.getType()) {
            case QueryArgumentType.INTEGER:
            case QueryArgumentType.TEXT:
                return "%" + argument.getValue() + "%";
            case QueryArgumentType.DATE:
                return argument.getValue().toString();
            case QueryArgumentType.BLOB:
                throw new TechnicalException("Search over a BLOB element is prohibited");
            default:
                throw new TechnicalException("Unknown query argument type");
        }
    }

}
