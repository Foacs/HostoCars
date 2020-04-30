package fr.vulture.hostocars.pojo;

import static fr.vulture.hostocars.TestHelper.validateBean;

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
        validateBean(Response.class);
    }

}
