package fr.vulture.hostocars.pojo;

import static fr.vulture.hostocars.TestHelper.validateBean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Mail} POJO.
 */
@DisplayName("Mail POJO")
class MailTest {

    /**
     * Tests the {@link Mail} POJO.
     */
    @Test
    @DisplayName("Testing the POJO")
    final void testBean() {
        validateBean(Mail.class);
    }

}
