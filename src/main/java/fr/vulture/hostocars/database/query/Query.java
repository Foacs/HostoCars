package fr.vulture.hostocars.database.query;

import static java.util.Objects.isNull;

import fr.vulture.hostocars.error.exception.TechnicalException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Wrapper for an SQL query and its arguments.
 */
@Value
@Builder
@Accessors(fluent = true)
public class Query {

    private final String statement;

    @Singular
    private final List<QueryArgument> arguments;

    /**
     * Builder for SQL query.
     */
    public static class QueryBuilder {

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

        private static final String EQUAL_OPERATOR = " = ";

        private static final String SPACE_SEPARATOR = " ";
        private static final String DOT_SEPARATOR = ".";
        private static final String COMMA_SEPARATOR = ", ";
        private static final String AND_SEPARATOR = " AND ";

        private static final String ALL_SYMBOL = "*";
        private static final String PARAMETER_SYMBOL = "?";

        private static final String OPENING_PARENTHESIS = "(";
        private static final String CLOSING_PARENTHESIS = ")";

        /**
         * Adds {@code SELECT *} to the current statement.
         *
         * @return the current {@link QueryBuilder}
         */
        public final QueryBuilder selectAll() {
            return this.append(new StringBuilder(SELECT_CLAUSE).append(ALL_SYMBOL).toString());
        }

        /**
         * Concatenates the given {@link String} to the current {@code statement}.
         *
         * @param statement
         *     The given {@link String} to concatenate
         *
         * @return this instance of {@link QueryBuilder}
         */
        private QueryBuilder append(final String statement) {
            this.statement = isNull(this.statement) ? statement : this.statement.concat(statement);

            this.arguments(Collections.emptyList());

            return this;
        }

        /**
         * Adds {@code SELECT *} to the current statement with the given table name.
         *
         * @param table
         *     The table name
         *
         * @return the current {@link QueryBuilder}
         */
        public final QueryBuilder selectAll(final String table) {
            return this.append(new StringBuilder(SELECT_CLAUSE).append(table).append(DOT_SEPARATOR).append(ALL_SYMBOL).toString());
        }

        /**
         * Adds {@code SELECT DISTINCT *} to the current statement.
         *
         * @return the current {@link QueryBuilder}
         */
        public final QueryBuilder selectAllDistinct() {
            return this.append(new StringBuilder(SELECT_CLAUSE).append(DISTINCT_CLAUSE).append(ALL_SYMBOL).toString());
        }

        /**
         * Adds a {@code SELECT} clause to the current statement with the given {@link SelectQueryArgument}.
         *
         * @param selectArgument
         *     The {@link SelectQueryArgument}
         *
         * @return the current {@link QueryBuilder}
         */
        public final QueryBuilder select(final SelectQueryArgument selectArgument) {
            return this.append(new StringBuilder(SELECT_CLAUSE)
                .append(selectArgument.table().toLowerCase(Locale.getDefault()))
                .append(DOT_SEPARATOR)
                .append(selectArgument.field())
                .toString());
        }

        /**
         * Adds a {@code SELECT} clause to the current statement with the given list of {@link SelectQueryArgument}.
         *
         * @param selectArguments
         *     The list of {@link SelectQueryArgument}
         *
         * @return the current {@link QueryBuilder}
         */
        public final QueryBuilder select(final Iterable<SelectQueryArgument> selectArguments) {
            final StringBuilder queryBuilder = new StringBuilder(SELECT_CLAUSE);

            final Iterator<SelectQueryArgument> iterator = selectArguments.iterator();
            while (iterator.hasNext()) {
                final SelectQueryArgument selectArgument = iterator.next();

                queryBuilder.append(selectArgument.table().toLowerCase(Locale.getDefault()))
                    .append(DOT_SEPARATOR)
                    .append(selectArgument.field());

                if (iterator.hasNext()) {
                    queryBuilder.append(COMMA_SEPARATOR);
                }
            }

            return this.append(queryBuilder.toString());
        }

        /**
         * Adds a {@code SELECT DISTINCT} clause to the current statement with the given {@link SelectQueryArgument}.
         *
         * @param selectArgument
         *     The {@link SelectQueryArgument}
         *
         * @return the current {@link QueryBuilder}
         */
        public final QueryBuilder selectDistinct(final SelectQueryArgument selectArgument) {
            return this.append(new StringBuilder(SELECT_CLAUSE)
                .append(DISTINCT_CLAUSE)
                .append(selectArgument.table().toLowerCase(Locale.getDefault()))
                .append(DOT_SEPARATOR)
                .append(selectArgument.field())
                .toString());
        }

        /**
         * Adds a {@code SELECT DISTINCT} clause to the current statement with the given list of {@link SelectQueryArgument}.
         *
         * @param selectArguments
         *     The list of {@link SelectQueryArgument}
         *
         * @return the current {@link QueryBuilder}
         */
        public final QueryBuilder selectDistinct(final Iterable<SelectQueryArgument> selectArguments) {
            final StringBuilder queryBuilder = new StringBuilder(SELECT_CLAUSE).append(DISTINCT_CLAUSE);

            final Iterator<SelectQueryArgument> iterator = selectArguments.iterator();
            while (iterator.hasNext()) {
                final SelectQueryArgument selectArgument = iterator.next();

                queryBuilder.append(selectArgument.table().toLowerCase(Locale.getDefault()))
                    .append(DOT_SEPARATOR)
                    .append(selectArgument.field());

                if (iterator.hasNext()) {
                    queryBuilder.append(COMMA_SEPARATOR);
                }
            }

            return this.append(queryBuilder.toString());
        }

        /**
         * Adds a {@code FROM} clause to the current statement with the given table name.
         *
         * @param table
         *     The table name
         *
         * @return the current {@link QueryBuilder}
         */
        public final QueryBuilder from(final String table) {
            return this.append(new StringBuilder(FROM_CLAUSE)
                .append(table)
                .append(SPACE_SEPARATOR)
                .append(table.toLowerCase(Locale.getDefault()))
                .toString());
        }

        /**
         * Adds a {@code FROM} clause to the current statement with the given tables names.
         *
         * @param tables
         *     The tables names
         *
         * @return the current {@link QueryBuilder}
         */
        public final QueryBuilder from(final Iterable<String> tables) {
            final StringBuilder queryBuilder = new StringBuilder(FROM_CLAUSE);

            final Iterator<String> iterator = tables.iterator();
            while (iterator.hasNext()) {
                final String table = iterator.next();

                queryBuilder.append(table)
                    .append(SPACE_SEPARATOR)
                    .append(table.toLowerCase(Locale.getDefault()));

                if (iterator.hasNext()) {
                    queryBuilder.append(COMMA_SEPARATOR);
                }
            }

            return this.append(queryBuilder.toString());
        }

        /**
         * Adds a {@code WHERE} clause to the current statement with the given {@link FilterQueryArgument}.
         *
         * @param whereArgument
         *     The {@link FilterQueryArgument}
         *
         * @return the current {@link QueryBuilder}
         *
         * @throws TechnicalException
         *     if a {@code WHERE} clause fails to be generated
         */
        public final QueryBuilder where(final WhereQueryArgument whereArgument) throws TechnicalException {
            this.append(new StringBuilder(WHERE_CLAUSE)
                .append(whereArgument.clause())
                .toString());

            if (whereArgument instanceof QueryArgument) {
                return this.argument((QueryArgument) whereArgument);
            }

            return this;
        }

        /**
         * Adds a {@code WHERE} clause to the current statement with the given list of {@link FilterQueryArgument}.
         *
         * @param whereArguments
         *     The list of {@link FilterQueryArgument}
         *
         * @return the current {@link QueryBuilder}
         *
         * @throws TechnicalException
         *     if a {@code WHERE} clause fails to be generated
         */
        public final QueryBuilder where(final Collection<WhereQueryArgument> whereArguments) throws TechnicalException {
            final StringBuilder queryBuilder = new StringBuilder(WHERE_CLAUSE);

            final Iterator<WhereQueryArgument> iterator = whereArguments.iterator();
            while (iterator.hasNext()) {
                final WhereQueryArgument argument = iterator.next();

                queryBuilder.append(argument.clause());

                if (iterator.hasNext()) {
                    queryBuilder.append(AND_SEPARATOR);
                }
            }

            return this.append(queryBuilder.toString()).arguments(whereArguments.stream()
                .filter(QueryArgument.class::isInstance)
                .map(QueryArgument.class::cast)
                .collect(Collectors.toList()));
        }

        /**
         * Adds an {@code ORDER BY} clause to the current statement with the given {@link OrderByQueryArgument}.
         *
         * @param orderByArgument
         *     The {@link OrderByQueryArgument}
         *
         * @return the current {@link QueryBuilder}
         */
        public final QueryBuilder orderBy(final OrderByQueryArgument orderByArgument) {
            return this.append(new StringBuilder(ORDER_BY_CLAUSE)
                .append(orderByArgument.table().toLowerCase(Locale.getDefault()))
                .append(DOT_SEPARATOR)
                .append(orderByArgument.column())
                .append(SPACE_SEPARATOR)
                .append(orderByArgument.order())
                .toString());
        }

        /**
         * Adds an {@code ORDER BY} clause to the current statement with the given list of {@link OrderByQueryArgument}.
         *
         * @param orderByArguments
         *     The list of {@link OrderByQueryArgument}
         *
         * @return the current {@link QueryBuilder}
         */
        public final QueryBuilder orderBy(final Iterable<OrderByQueryArgument> orderByArguments) {
            final StringBuilder queryBuilder = new StringBuilder(ORDER_BY_CLAUSE);

            final Iterator<OrderByQueryArgument> iterator = orderByArguments.iterator();
            while (iterator.hasNext()) {
                final OrderByQueryArgument argument = iterator.next();

                queryBuilder.append(argument.table().toLowerCase(Locale.getDefault()))
                    .append(DOT_SEPARATOR)
                    .append(argument.column())
                    .append(SPACE_SEPARATOR)
                    .append(argument.order());

                if (iterator.hasNext()) {
                    queryBuilder.append(COMMA_SEPARATOR);
                }
            }

            return this.append(queryBuilder.toString());
        }

        /**
         * Adds an {@code INSERT} clause to the current statement with the given table name and list of {@link QueryArgument}.
         *
         * @param table
         *     The table name
         * @param insertArguments
         *     The list of {@link QueryArgument}
         *
         * @return the current {@link QueryBuilder}
         */
        public final QueryBuilder insert(final String table, final Collection<QueryArgument> insertArguments) {
            final StringBuilder queryBuilder =
                new StringBuilder(INSERT_INTO_CLAUSE).append(table).append(SPACE_SEPARATOR).append(OPENING_PARENTHESIS);

            final Iterator<QueryArgument> columnIterator = insertArguments.iterator();
            while (columnIterator.hasNext()) {
                final QueryArgument argument = columnIterator.next();

                queryBuilder.append(argument.column());

                if (columnIterator.hasNext()) {
                    queryBuilder.append(COMMA_SEPARATOR);
                }
            }

            queryBuilder.append(CLOSING_PARENTHESIS).append(VALUES_CLAUSE).append(OPENING_PARENTHESIS);

            final Iterator<QueryArgument> parameterIterator = insertArguments.iterator();
            while (parameterIterator.hasNext()) {
                parameterIterator.next();

                queryBuilder.append(PARAMETER_SYMBOL);

                if (parameterIterator.hasNext()) {
                    queryBuilder.append(COMMA_SEPARATOR);
                }
            }

            return this.append(queryBuilder.append(CLOSING_PARENTHESIS).toString()).arguments(insertArguments);
        }

        /**
         * Adds an {@code UPDATE} clause to the current statement with the given table name and list of {@link QueryArgument}.
         *
         * @param table
         *     The table name
         * @param updateArguments
         *     The list of {@link QueryArgument}
         *
         * @return the current {@link QueryBuilder}
         */
        public final QueryBuilder update(final String table, final Collection<QueryArgument> updateArguments) {
            final StringBuilder queryBuilder = new StringBuilder(UPDATE_CLAUSE).append(table).append(SET_CLAUSE);

            final Iterator<QueryArgument> iterator = updateArguments.iterator();
            while (iterator.hasNext()) {
                final QueryArgument argument = iterator.next();

                queryBuilder.append(argument.column()).append(EQUAL_OPERATOR).append(PARAMETER_SYMBOL);

                if (iterator.hasNext()) {
                    queryBuilder.append(COMMA_SEPARATOR);
                }
            }

            return this.append(queryBuilder.toString()).arguments(updateArguments);
        }

        /**
         * Adds a {@code DELETE} clause to the current statement with the given table name.
         *
         * @param table
         *     The table name
         *
         * @return the current {@link QueryBuilder}
         */
        public final QueryBuilder delete(final String table) {
            return this.append(new StringBuilder(DELETE_CLAUSE).append(FROM_CLAUSE).append(table).toString());
        }

    }

}
