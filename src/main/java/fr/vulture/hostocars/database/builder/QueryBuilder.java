package fr.vulture.hostocars.database.builder;

import static java.sql.Types.BLOB;
import static java.sql.Types.DATE;
import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;
import static java.util.Objects.nonNull;

import fr.vulture.hostocars.exception.TechnicalException;
import java.sql.Types;
import java.util.Iterator;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Builder component for SQL queries with arguments.
 */
@NoArgsConstructor
public class QueryBuilder {

    private static final String SELECT_CLAUSE = "SELECT ";
    private static final String DISTINCT_CLAUSE = "DISTINCT ";
    private static final String INSERT_INTO_CLAUSE = "INSERT INTO ";
    private static final String VALUES_CLAUSE = " VALUES ";
    private static final String UPDATE_CLAUSE = "UPDATE ";
    private static final String SET_CLAUSE = " SET ";
    private static final String DELETE_CLAUSE = "DELETE";
    private static final String FROM_CLAUSE = " FROM ";
    private static final String WHERE_CLAUSE = " WHERE ";
    private static final String ORDER_BY_CLAUSE = " ORDER BY ";
    private static final String ASCENDING_CLAUSE = " ASC";
    private static final String DESCENDING_CLAUSE = " DESC";

    private static final String EQUAL_OPERATOR = " = ";
    private static final String LIKE_OPERATOR = " LIKE ";
    private static final String IS_OPERATOR = " IS ";

    private static final String COMMA_SEPARATOR = ",";
    private static final String AND_SEPARATOR = " AND ";

    private static final String ALL_SYMBOL = "*";
    private static final String LIKE_SYMBOL = "%";
    private static final String PARAMETER_SYMBOL = "?";

    private static final String OPENING_PARENTHESIS = "(";
    private static final String CLOSING_PARENTHESIS = ")";

    @Getter
    private final Query query = new Query();

    /**
     * Builds a basic SELECT ALL query from the {@code tableName} table.
     *
     * @param tableName
     *     The name of the table
     * @param distinct
     *     If the SELECT clause has to be completed with a DISTINCT clause
     *
     * @return the current instance of {@link QueryBuilder}
     */
    public QueryBuilder buildSelectQuery(@NotNull final String tableName, final boolean distinct) {
        // Initializes the query with a SELECT clause
        final StringBuilder queryBuilder = new StringBuilder(SELECT_CLAUSE);

        // If the distinct flag is true, adds a DISTINCT clause to the SELECT
        if (distinct) {
            queryBuilder.append(DISTINCT_CLAUSE);
        }

        // Adds the ALL operator, FROM clause and table name
        queryBuilder.append(ALL_SYMBOL)
            .append(FROM_CLAUSE)
            .append(tableName);

        // Updates the query of the builder
        query.setQuery(queryBuilder.toString());

        // Returns the builder
        return this;
    }

    /**
     * Builds a basic SELECT query from the {@code tableName} table with the input column names.
     *
     * @param tableName
     *     The name of the table
     * @param columnNames
     *     The column names used for the SELECT clause
     * @param distinct
     *     If the SELECT clause has to be completed with a DISTINCT clause
     *
     * @return the current instance of {@link QueryBuilder}
     */
    public QueryBuilder buildSelectQuery(@NotNull final String tableName, @NotNull @NotEmpty final List<String> columnNames, final boolean distinct) {
        // Initializes the query with a SELECT clause
        final StringBuilder queryBuilder = new StringBuilder(SELECT_CLAUSE);

        // If the distinct flag is true, adds a DISTINCT clause to the SELECT
        if (distinct) {
            queryBuilder.append(DISTINCT_CLAUSE);
        }

        // Adds the fields names for the SELECT clause
        final Iterator<String> iterator = columnNames.iterator();
        while (iterator.hasNext()) {
            final String columnName = iterator.next();

            // Adds the field name
            queryBuilder.append(columnName);

            // If there is a next argument, adds a comma separator
            if (iterator.hasNext()) {
                queryBuilder.append(COMMA_SEPARATOR);
            }
        }

        // Adds the FROM clause and table name
        queryBuilder.append(FROM_CLAUSE).append(tableName);

        // Updates the query of the builder
        query.setQuery(queryBuilder.toString());

        // Returns the builder
        return this;
    }

    /**
     * Builds an INSERT INTO query from the {@code tableName} table with the input list of {@link QueryArgument}.
     *
     * @param tableName
     *     The table name
     * @param arguments
     *     The query arguments for the INSERT INTO clause
     *
     * @return the current instance of {@link QueryBuilder}
     */
    public QueryBuilder buildInsertQuery(@NotNull final String tableName, @NotNull @NotEmpty final List<QueryArgument> arguments) {
        // Initializes the query with an INSERT INTO clause, the table name and an opening parenthesis
        final StringBuilder queryBuilder = new StringBuilder(INSERT_INTO_CLAUSE).append(tableName).append(OPENING_PARENTHESIS);

        // Adds the fields names for the INSERT INTO clause
        final Iterator<QueryArgument> iterator = arguments.iterator();
        while (iterator.hasNext()) {
            final String columnName = iterator.next().getName();

            // Adds the field name
            queryBuilder.append(columnName);

            // If there is a next argument, adds a comma separator
            if (iterator.hasNext()) {
                queryBuilder.append(COMMA_SEPARATOR);
            }
        }

        // Adds a closing parenthesis, a VALUES clause and an opening parenthesis
        queryBuilder.append(CLOSING_PARENTHESIS).append(VALUES_CLAUSE).append(OPENING_PARENTHESIS);

        // Adds the parameters symbols for the VALUES clause
        for (int i = 1; i <= arguments.size(); i++) {
            // Adds a parameter symbol
            queryBuilder.append(PARAMETER_SYMBOL);

            // If there is a next argument, adds a comma separator
            if (i < arguments.size()) {
                queryBuilder.append(COMMA_SEPARATOR);
            }
        }

        // Adds a closing parenthesis
        queryBuilder.append(CLOSING_PARENTHESIS);

        // Updates the query of the builder
        query.setQuery(queryBuilder.toString());

        // Sets the query arguments
        query.setArguments(arguments);

        return this;
    }

    /**
     * Builds an UPDATE query from the {@code tableName} table with the input lists of {@link QueryArgument}.
     *
     * @param tableName
     *     The table name
     * @param updateArguments
     *     The query arguments for the UPDATE clause
     * @param whereArguments
     *     The query arguments for the WHERE clause
     *
     * @return the current instance of {@link QueryBuilder}
     *
     * @throws TechnicalException
     *     if a {@link QueryArgument} for the WHERE clause is of prohibited or unknown type
     */
    public QueryBuilder buildUpdateQuery(@NotNull final String tableName, @NotNull @NotEmpty final List<QueryArgument> updateArguments,
        @NotNull @NotEmpty List<QueryArgument> whereArguments) throws TechnicalException {
        // Initializes the query with an UPDATE clause, the table name and a SET clause
        final StringBuilder queryBuilder = new StringBuilder(UPDATE_CLAUSE).append(tableName).append(SET_CLAUSE);

        // Adds the fields names and parameter symbols for the UPDATE clause
        final Iterator<QueryArgument> iterator = updateArguments.iterator();
        while (iterator.hasNext()) {
            final String columnName = iterator.next().getName();

            // Adds the field name, an equal operator and a parameter symbol
            queryBuilder.append(columnName).append(EQUAL_OPERATOR).append(PARAMETER_SYMBOL);

            // If there is a next argument, adds a comma separator
            if (iterator.hasNext()) {
                queryBuilder.append(COMMA_SEPARATOR);
            }
        }

        // Updates the query of the builder
        query.setQuery(queryBuilder.toString());

        // Sets the query arguments
        query.setArguments(updateArguments);

        return addWhereClause(whereArguments);
    }

    /**
     * Builds a DELETE query from the {@code tableName} table with the input list of {@link QueryArgument}.
     *
     * @param tableName
     *     The table name
     * @param arguments
     *     The query arguments for the WHERE clause
     *
     * @return the current instance of {@link QueryBuilder}
     *
     * @throws TechnicalException
     *     if a {@link QueryArgument} for the WHERE clause is of prohibited or unknown type
     */
    public QueryBuilder buildDeleteQuery(@NotNull final String tableName, @NotNull @NotEmpty final List<QueryArgument> arguments)
        throws TechnicalException {
        // Initializes the query with a DELETE clause, a FROM clause and the table name
        final StringBuilder queryBuilder = new StringBuilder(DELETE_CLAUSE).append(FROM_CLAUSE).append(tableName);

        // Updates the query of the builder
        query.setQuery(queryBuilder.toString());

        return addWhereClause(arguments);
    }

    /**
     * Adds a WHERE clause to the current query, depending on the input list of {@link QueryArgument}.
     *
     * @param arguments
     *     The query arguments for the WHERE clause
     *
     * @return the current instance of {@link QueryBuilder}
     *
     * @throws TechnicalException
     *     if a {@link QueryArgument} is of prohibited or unknown type
     */
    public QueryBuilder addWhereClause(@NotNull @NotEmpty final List<QueryArgument> arguments) throws TechnicalException {
        // Adds the WHERE clause to the current query
        final StringBuilder queryBuilder = new StringBuilder(query.getQuery()).append(WHERE_CLAUSE);

        // For each query argument, adds it to the query and its arguments list
        final Iterator<QueryArgument> iterator = arguments.iterator();
        while (iterator.hasNext()) {
            final QueryArgument argument = iterator.next();

            // Adds the operator that corresponds to the argument type for the WHERE clause followed by the parameter symbol
            queryBuilder.append(argument.getName())
                .append(getWhereClauseOperator(argument))
                .append(PARAMETER_SYMBOL);

            // If there is a next argument, adds an AND clause
            if (iterator.hasNext()) {
                queryBuilder.append(AND_SEPARATOR);
            }

            // Transforms the argument value according to its type for the WHERE clause
            argument.setValue(getWhereClauseValue(argument));
            query.getArguments().add(argument);
        }

        // Updates the query of the builder
        query.setQuery(queryBuilder.toString());

        // Returns the builder
        return this;
    }

    /**
     * Adds an ORDER BY clause to the current query with the input column names, and which order can be inverted with the {@code invertOrder} flag.
     *
     * @param columnNames
     *     The column names used for the ORDER BY clause
     * @param invertOrder
     *     Flag allowing to invert the natural order of the ORDER BY clause (setting it at true implies a descending order)
     *
     * @return the current instance of {@link QueryBuilder}
     */
    public QueryBuilder addOrderByClause(@NotNull @NotEmpty final List<String> columnNames, final boolean invertOrder) {
        // Adds the ORDER BY clause to the current query
        final StringBuilder queryBuilder = new StringBuilder(query.getQuery()).append(ORDER_BY_CLAUSE);

        // For each column name, adds it to the query
        final Iterator<String> iterator = columnNames.iterator();
        while (iterator.hasNext()) {
            final String columnName = iterator.next();

            // Adds the column name to the ORDER BY clause
            queryBuilder.append(columnName);

            // If there is a next column name, adds a comma separator
            if (iterator.hasNext()) {
                queryBuilder.append(COMMA_SEPARATOR);
            }
        }

        // Adds the ASC or DESC clause depending on the invertOrder flag
        queryBuilder.append(invertOrder ? DESCENDING_CLAUSE : ASCENDING_CLAUSE);

        // Updates the query of the builder
        query.setQuery(queryBuilder.toString());

        // Returns the builder
        return this;
    }

    /**
     * Returns the operator that corresponds to the input argument given its type (see {@link Types}) and value for a WHERE clause.
     *
     * @param argument
     *     The query argument
     *
     * @return an SQL operator
     *
     * @throws TechnicalException
     *     if the argument type is prohibited or unknown
     */
    String getWhereClauseOperator(@NotNull final QueryArgument argument) throws TechnicalException {
        switch (argument.getType()) {
            case INTEGER:
            case DATE:
                return nonNull(argument.getValue()) ? EQUAL_OPERATOR : IS_OPERATOR;
            case VARCHAR:
                return nonNull(argument.getValue()) ? LIKE_OPERATOR : IS_OPERATOR;
            case BLOB:
                throw new TechnicalException("Search over a BLOB element is prohibited");
            default:
                throw new TechnicalException("Unknown query argument type");
        }
    }

    /**
     * Returns the value that corresponds to the input argument given its type (see {@link Types}) and value for a WHERE clause.
     *
     * @param argument
     *     The query argument
     *
     * @return a value
     *
     * @throws TechnicalException
     *     if the argument type is prohibited or unknown
     */
    Object getWhereClauseValue(@NotNull final QueryArgument argument) throws TechnicalException {
        switch (argument.getType()) {
            case INTEGER:
            case DATE:
                return argument.getValue();
            case VARCHAR:
                return LIKE_SYMBOL + argument.getValue() + LIKE_SYMBOL;
            case BLOB:
                throw new TechnicalException("Search over a BLOB element is prohibited");
            default:
                throw new TechnicalException("Unknown query argument type");
        }
    }

}
