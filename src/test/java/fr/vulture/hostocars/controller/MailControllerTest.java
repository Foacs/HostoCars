package fr.vulture.hostocars.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link MailController} class.
 */
@DisplayName("Mail controller")
class MailControllerTest {

    private final MailController mailController = new MailController();

    /**
     * Tests the {@link MailController#sendMail} method with a null map of details.
     */
    @Test
    @DisplayName("Send mail (null map of details)")
    void testSendMailWithNullMapOfDetails() {
        // Calls the method
        assertThrows(NullPointerException.class, () -> this.mailController.sendMail(null));
    }

}
