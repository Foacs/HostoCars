package fr.vulture.hostocars.database.builder;

import fr.vulture.hostocars.TestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link QueryArgument} bean.
 */
@DisplayName("Query argument")
class QueryArgumentTest {

    /**
     * Tests the {@link QueryArgument} bean.
     */
    @Test
    @DisplayName("Validating the bean")
    void testBeanValidation() {
        TestHelper.validateBean(QueryArgument.class);
    }

}
