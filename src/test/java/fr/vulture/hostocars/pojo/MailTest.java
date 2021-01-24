package fr.vulture.hostocars.pojo;

import static fr.vulture.hostocars.TestHelper.validateBean;

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
    @DisplayName("Bean validation")
    final void testBean() {
        validateBean(Mail.class);
    }

}
