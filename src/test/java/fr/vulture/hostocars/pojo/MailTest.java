package fr.vulture.hostocars.pojo;

import static fr.vulture.hostocars.TestHelper.validatePojo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Mail} class.
 */
@DisplayName("Mail")
class MailTest {

    /**
     * Tests the {@link Mail} class.
     */
    @Test
    @DisplayName("POJO validation")
    final void testPojo() {
        validatePojo(Mail.class);
    }

}
