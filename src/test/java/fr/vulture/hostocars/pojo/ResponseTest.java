package fr.vulture.hostocars.pojo;

import fr.vulture.hostocars.TestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Response} POJO.
 */
@DisplayName("Response POJO")
class ResponseTest {

    /**
     * Tests the {@link Response} POJO.
     */
    @Test
    @DisplayName("Testing the POJO")
    final void testBean() {
        TestHelper.validateBean(Response.class);
    }

}
