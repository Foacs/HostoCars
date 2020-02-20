package fr.vulture.hostocars.pojo;

import fr.vulture.hostocars.TestHelper;
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
        TestHelper.validateBean(Mail.class);
    }

}
