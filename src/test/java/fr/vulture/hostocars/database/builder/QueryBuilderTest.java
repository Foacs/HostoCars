package fr.vulture.hostocars.database.builder;

import static java.sql.Types.BLOB;
import static java.sql.Types.DATE;
import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.collect.Lists;
import fr.vulture.hostocars.exception.TechnicalException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link QueryBuilder} class.
 */
@DisplayName("Query builder")
class QueryBuilderTest {

    private static final byte[] EMPTY_BLOB = {};
    private static final int UNKNOWN_TYPE = 999;
    private static QueryBuilder queryBuilder;

    /**
     * Initializes the {@link QueryBuilder}.
     */
    @BeforeEach
    final void init() {
        queryBuilder = new QueryBuilder();
    }

    /**
     * Tests the {@link QueryBuilder#buildSelectQuery(String, boolean)} method without DISTINCT clause.
     */
    @Test
    @DisplayName("Building a SELECT ALL query without DISTINCT clause")
    final void testBuildSelectAllQueryWithoutDistinctClause() {
        final String tableName = "tableName";

        final String expectedQuery = "SELECT * FROM " + tableName;

        final Query result = queryBuilder.buildSelectQuery(tableName, false).getQuery();
        assertNotNull(result, "Result object unexpectedly null");
        assertAll("Asserting result object",
            () -> assertEquals(expectedQuery, result.getQuery(), "Query different from expected"),
            () -> assertTrue(result.getArguments().isEmpty(), "No query argument expected")
        );
    }

    /**
     * Tests the {@link QueryBuilder#buildSelectQuery(String, boolean)} method with a DISTINCT clause.
     */
    @Test
    @DisplayName("Building a SELECT ALL query with a DISTINCT clause")
    final void testBuildSelectAllQueryWithDistinctClause() {
        final String tableName = "tableName";

        final String expectedQuery = "SELECT DISTINCT * FROM " + tableName;

        final Query result = queryBuilder.buildSelectQuery(tableName, true).getQuery();
        assertNotNull(result, "Result object unexpectedly null");
        assertAll("Asserting result object",
            () -> assertEquals(expectedQuery, result.getQuery(), "Query different from expected"),
            () -> assertTrue(result.getArguments().isEmpty(), "No query argument expected")
        );
    }

    /**
     * Tests the {@link QueryBuilder#buildSelectQuery(String, Iterable, boolean)} method without DISTINCT clause.
     */
    @Test
    @DisplayName("Building a SELECT query without DISTINCT clause")
    final void testBuildSelectQueryWithoutDistinctClause() {
        final String tableName = "tableName";
        final List<String> columnNames = Lists.newArrayList(
            "column1", "column2", "column3"
        );

        final String expectedQuery = "SELECT " + columnNames.get(0) + "," + columnNames.get(1) + "," + columnNames.get(2) + " FROM " + tableName;

        final Query result = queryBuilder.buildSelectQuery(tableName, columnNames, false).getQuery();
        assertNotNull(result, "Result object unexpectedly null");
        assertAll("Asserting result object",
            () -> assertEquals(expectedQuery, result.getQuery(), "Query different from expected"),
            () -> assertTrue(result.getArguments().isEmpty(), "No query argument expected")
        );
    }

    /**
     * Tests the {@link QueryBuilder#buildSelectQuery(String, Iterable, boolean)} method with a DISTINCT clause.
     */
    @Test
    @DisplayName("Building a SELECT query with a DISTINCT clause")
    final void testBuildSelectQueryWithDistinctClause() {
        final String tableName = "tableName";
        final List<String> columnNames = Lists.newArrayList(
            "column1", "column2", "column3"
        );

        final String expectedQuery =
            "SELECT DISTINCT " + columnNames.get(0) + "," + columnNames.get(1) + "," + columnNames.get(2) + " FROM " + tableName;

        final Query result = queryBuilder.buildSelectQuery(tableName, columnNames, true).getQuery();
        assertNotNull(result, "Result object unexpectedly null");
        assertAll("Asserting result object",
            () -> assertEquals(expectedQuery, result.getQuery(), "Query different from expected"),
            () -> assertTrue(result.getArguments().isEmpty(), "No query argument expected")
        );
    }

    /**
     * Tests the {@link QueryBuilder#buildInsertQuery(String, List)} method.
     */
    @Test
    @DisplayName("Building an INSERT INTO query")
    final void testBuildInsertIntoQuery() {
        final String tableName = "tableName";
        final String[] columnNames = { "column1", "column2", "column3", "column4" };
        final List<QueryArgument> queryArguments = Lists.newArrayList(
            new QueryArgument(columnNames[0], 1, INTEGER),
            new QueryArgument(columnNames[1], LocalDate.now(), DATE),
            new QueryArgument(columnNames[2], "", VARCHAR),
            new QueryArgument(columnNames[3], EMPTY_BLOB, BLOB)
        );

        final String expectedQuery =
            "INSERT INTO " + tableName + "(" + columnNames[0] + "," + columnNames[1] + "," + columnNames[2] + "," + columnNames[3] +
                ") VALUES (?,?,?,?)";

        final Query result = queryBuilder.buildInsertQuery(tableName, queryArguments).getQuery();
        assertNotNull(result, "Result object unexpectedly null");
        assertAll("Asserting result object",
            () -> assertEquals(expectedQuery, result.getQuery(), "Query different from expected"),
            () -> assertEquals(queryArguments, result.getArguments(), "Query arguments different from expected")
        );
    }

    /**
     * Tests the {@link QueryBuilder#buildUpdateQuery(String, List, Iterable)} method.
     *
     * @throws TechnicalException
     *     see {@link QueryBuilder#buildUpdateQuery(String, List, Iterable)}
     */
    @Test
    @DisplayName("Building an UPDATE query")
    final void testBuildUpdateQuery() throws TechnicalException {
        final String tableName = "tableName";
        final String[] updateNames = { "updateColumn1", "updateColumn2", "updateColumn3", "updateColumn4" };
        final List<QueryArgument> updateArguments = Lists.newArrayList(
            new QueryArgument(updateNames[0], 1, INTEGER),
            new QueryArgument(updateNames[1], LocalDate.now(), DATE),
            new QueryArgument(updateNames[2], "", VARCHAR),
            new QueryArgument(updateNames[3], EMPTY_BLOB, BLOB)
        );
        final Collection<QueryArgument> queryArguments = new ArrayList<>(updateArguments);
        final String[] whereNames = { "whereColumn1", "whereColumn2", "whereColumn3" };
        final List<QueryArgument> whereArguments = Lists.newArrayList(
            new QueryArgument(whereNames[0], 1, INTEGER),
            new QueryArgument(whereNames[1], LocalDate.now(), DATE),
            new QueryArgument(whereNames[2], "", VARCHAR)
        );
        queryArguments.addAll(whereArguments);

        final String expectedQuery =
            "UPDATE " + tableName + " SET " + updateNames[0] + " = ?," + updateNames[1] + " = ?," + updateNames[2] + " = ?," + updateNames[3] +
                " = ? WHERE " + whereNames[0] + " = ? AND " + whereNames[1] + " = ? AND " + whereNames[2] + " LIKE ?";

        final Query result = queryBuilder.buildUpdateQuery(tableName, updateArguments, whereArguments).getQuery();
        assertNotNull(result, "Result object unexpectedly null");
        assertAll("Asserting result object",
            () -> assertEquals(expectedQuery, result.getQuery(), "Query different from expected"),
            () -> assertEquals(queryArguments, result.getArguments(), "Query arguments different from expected")
        );
    }

    /**
     * Tests the {@link QueryBuilder#buildDeleteQuery(String, Iterable)} method.
     *
     * @throws TechnicalException
     *     see {@link QueryBuilder#buildDeleteQuery(String, Iterable)}
     */
    @Test
    @DisplayName("Building a DELETE query")
    final void testBuildDeleteQuery() throws TechnicalException {
        final String tableName = "tableName";
        final String[] columnNames = { "column1", "column2", "column3" };
        final List<QueryArgument> queryArguments = Lists.newArrayList(
            new QueryArgument(columnNames[0], 1, INTEGER),
            new QueryArgument(columnNames[1], LocalDate.now(), DATE),
            new QueryArgument(columnNames[2], "", VARCHAR)
        );

        final String expectedQuery =
            "DELETE FROM " + tableName + " WHERE " + columnNames[0] + " = ? AND " + columnNames[1] + " = ? AND " + columnNames[2] + " LIKE ?";

        final Query result = queryBuilder.buildDeleteQuery(tableName, queryArguments).getQuery();
        assertNotNull(result, "Result object unexpectedly null");
        assertAll("Asserting result object",
            () -> assertEquals(expectedQuery, result.getQuery(), "Query different from expected"),
            () -> assertEquals(queryArguments, result.getArguments(), "Query arguments different from expected")
        );
    }

    /**
     * Tests the {@link QueryBuilder#addWhereClause(Iterable)} method.
     *
     * @throws TechnicalException
     *     see {@link QueryBuilder#addWhereClause(Iterable)}
     */
    @Test
    @DisplayName("Adding a WHERE clause")
    final void testAddWhereClause() throws TechnicalException {
        final String tableName = "tableName";
        final String[] columnNames = { "column1", "column2", "column3" };
        final List<QueryArgument> queryArguments = Lists.newArrayList(
            new QueryArgument(columnNames[0], 1, INTEGER),
            new QueryArgument(columnNames[1], LocalDate.now(), DATE),
            new QueryArgument(columnNames[2], "", VARCHAR)
        );

        final String expectedQuery =
            "SELECT * FROM " + tableName + " WHERE " + columnNames[0] + " = ? AND " + columnNames[1] + " = ? AND " + columnNames[2] + " LIKE ?";

        final Query result = queryBuilder.buildSelectQuery(tableName, false).addWhereClause(queryArguments).getQuery();
        assertNotNull(result, "Result object unexpectedly null");
        assertAll("Asserting result object",
            () -> assertEquals(expectedQuery, result.getQuery(), "Query different from expected"),
            () -> assertEquals(queryArguments, result.getArguments(), "Query arguments different from expected")
        );
    }

    /**
     * Tests the {@link QueryBuilder#addOrderByClause(Iterable, boolean)} method with an ASC order.
     */
    @Test
    @DisplayName("Adding an ORDER BY clause with an ASC order")
    final void testAddOrderByClauseWithAscOrder() {
        final String tableName = "tableName";
        final List<String> columnNames = Lists.newArrayList("column1", "column2", "column3");

        final String expectedQuery =
            "SELECT * FROM " + tableName + " ORDER BY " + columnNames.get(0) + "," + columnNames.get(1) + "," + columnNames.get(2) + " ASC";

        final Query result = queryBuilder.buildSelectQuery(tableName, false).addOrderByClause(columnNames, false).getQuery();
        assertNotNull(result, "Result object unexpectedly null");
        assertAll("Asserting result object",
            () -> assertEquals(expectedQuery, result.getQuery(), "Query different from expected"),
            () -> assertTrue(result.getArguments().isEmpty(), "No query argument expected")
        );
    }

    /**
     * Tests the {@link QueryBuilder#addOrderByClause(Iterable, boolean)} method with a DESC order.
     */
    @Test
    @DisplayName("Adding an ORDER BY clause with a DESC order")
    final void testAddOrderByClauseWithDescOrder() {
        final String tableName = "tableName";
        final List<String> columnNames = Lists.newArrayList("column1", "column2", "column3");

        final String expectedQuery =
            "SELECT * FROM " + tableName + " ORDER BY " + columnNames.get(0) + "," + columnNames.get(1) + "," + columnNames.get(2) + " DESC";

        final Query result = queryBuilder.buildSelectQuery(tableName, false).addOrderByClause(columnNames, true).getQuery();
        assertNotNull(result, "Result object unexpectedly null");
        assertAll("Asserting result object",
            () -> assertEquals(expectedQuery, result.getQuery(), "Query different from expected"),
            () -> assertTrue(result.getArguments().isEmpty(), "No query argument expected")
        );
    }

    /**
     * Tests the {@link QueryBuilder#getWhereClauseOperator(QueryArgument)} method in all cases.
     */
    @Test
    @DisplayName("Get WHERE clause operators in all cases")
    final void testGetWhereClauseOperator() {
        assertAll("Asserting all cases",
            () -> assertEquals(" IS ", QueryBuilder.getWhereClauseOperator(new QueryArgument("", null, INTEGER)),
                "Returned operator different from expected"),
            () -> assertEquals(" = ", QueryBuilder.getWhereClauseOperator(new QueryArgument("", 1, INTEGER)),
                "Returned operator different from expected"),
            () -> assertEquals(" IS ", QueryBuilder.getWhereClauseOperator(new QueryArgument("", null, DATE)),
                "Returned operator different from expected"),
            () -> assertEquals(" = ", QueryBuilder.getWhereClauseOperator(new QueryArgument("", LocalDate.now(), DATE)),
                "Returned operator different from expected"),
            () -> assertEquals(" IS ", QueryBuilder.getWhereClauseOperator(new QueryArgument("", null, VARCHAR)),
                "Returned operator different from expected"),
            () -> assertEquals(" LIKE ", QueryBuilder.getWhereClauseOperator(new QueryArgument("", "", VARCHAR)),
                "Returned operator different from expected"),
            () -> assertEquals("Search over a BLOB element is prohibited",
                assertThrows(TechnicalException.class, () -> QueryBuilder.getWhereClauseOperator(new QueryArgument("", null, BLOB)),
                    "Expected exception has not been thrown").getMessage(), "Thrown exception message different from expected"),
            () -> assertEquals("Unknown query argument type",
                assertThrows(TechnicalException.class, () -> QueryBuilder.getWhereClauseOperator(new QueryArgument("", null, UNKNOWN_TYPE)),
                    "Expected exception has not been thrown").getMessage(), "Thrown exception message different from expected")
        );
    }

    /**
     * Tests the {@link QueryBuilder#getWhereClauseValue(QueryArgument)} method in all cases.
     */
    @Test
    @DisplayName("Get WHERE clause value in all cases")
    final void testGetWhereClauseValue() {
        final int integerValue = 1;
        final LocalDate dateValue = LocalDate.now();
        final String varcharValue = "varchar";
        assertAll("Asserting all cases",
            () -> assertEquals(integerValue, QueryBuilder.getWhereClauseValue(new QueryArgument("", integerValue, INTEGER)),
                "Returned value different from expected"),
            () -> assertEquals(dateValue, QueryBuilder.getWhereClauseValue(new QueryArgument("", dateValue, DATE)),
                "Returned value different from expected"),
            () -> assertEquals("%" + varcharValue + "%", QueryBuilder.getWhereClauseValue(new QueryArgument("", varcharValue, VARCHAR)),
                "Returned value different from expected"),
            () -> assertEquals("Search over a BLOB element is prohibited",
                assertThrows(TechnicalException.class, () -> QueryBuilder.getWhereClauseValue(new QueryArgument("", null, BLOB)),
                    "Expected exception has not been thrown").getMessage(), "Thrown exception message different from expected"),
            () -> assertEquals("Unknown query argument type",
                assertThrows(TechnicalException.class, () -> QueryBuilder.getWhereClauseValue(new QueryArgument("", null, UNKNOWN_TYPE)),
                    "Expected exception has not been thrown").getMessage(), "Thrown exception message different from expected")
        );
    }

}
