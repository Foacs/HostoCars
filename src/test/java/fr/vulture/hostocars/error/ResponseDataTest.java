package fr.vulture.hostocars.error;

import fr.vulture.hostocars.TestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link ResponseData} bean.
 */
@DisplayName("Response data")
public class ResponseDataTest {

    /**
     * Tests the {@link ResponseData} bean.
     */
    @Test
    @DisplayName("Validating the bean")
    final void testBeanValidation() {
        TestHelper.validateBean(ResponseData.class);
    }

}
