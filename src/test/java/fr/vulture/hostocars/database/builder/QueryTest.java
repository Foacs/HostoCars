package fr.vulture.hostocars.database.builder;

import fr.vulture.hostocars.TestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Query} bean.
 */
@DisplayName("Query")
class QueryTest {

    /**
     * Tests the {@link Query} bean.
     */
    @Test
    @DisplayName("Validating the bean")
    final void testBeanValidation() {
        TestHelper.validateBean(Query.class);
    }

}
