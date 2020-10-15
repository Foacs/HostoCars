package fr.vulture.hostocars.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import fr.vulture.hostocars.pojo.Mail;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Test class for the {@link MailController} class.
 */
@DisplayName("Mail controller")
@ExtendWith(MockitoExtension.class)
class MailControllerTest {

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private MailController mailController;

    /**
     * Initialization method called before each test.
     */
    @BeforeEach
    @SneakyThrows
    void initialize() {
        this.folder.create();
    }

    /**
     * Termination method called after each test.
     */
    @AfterEach
    void terminate() {
        this.folder.delete();
    }

    /**
     * Tests the {@link MailController#sendMail} method.
     */
    @Test
    @SneakyThrows
    @DisplayName("Sending a mail")
    final void testSendMail() {
        final Mail mail = new Mail();
        final String recipient = "localPart@domain";
        mail.setRecipient(recipient);
        final String subject = "subject";
        mail.setSubject(subject);
        final String content = "content";
        mail.setContent(content);
        final String pathToFirstAttachment = "pathToFirstAttachment";
        final String pathToSecondAttachment = "pathToSecondAttachment";
        mail.setAttachmentArray(new String[] {pathToFirstAttachment, pathToSecondAttachment});
        final MimeMessage message = new MimeMessage((Session) null);

        this.folder.newFile(pathToFirstAttachment);
        this.folder.newFile(pathToSecondAttachment);

        when(this.mailSender.createMimeMessage()).thenReturn(message);
        doNothing().when(this.mailSender).send(message);

        final ResponseEntity<?> response = this.mailController.sendMail(mail);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(NO_CONTENT, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");
        assertEquals(recipient, message.getAllRecipients()[0].toString(), "Recipient different from expected");
        assertEquals(subject, message.getSubject(), "Subject different from expected");
        assertDoesNotThrow(message::getContent, "Content unexpectedly null");

        verify(this.mailSender).createMimeMessage();
        verify(this.mailSender).send(message);
    }

    /**
     * Tests the {@link MailController#sendMail} method's error case.
     */
    @Test
    @SneakyThrows
    @DisplayName("Sending a mail in error")
    final void testSendMailInError() {
        final MimeMessage message = new MimeMessage((Session) null);

        when(this.mailSender.createMimeMessage()).thenReturn(message);

        assertThrows(IllegalArgumentException.class, () -> this.mailController.sendMail(new Mail()), "Expected exception not thrown");

        verify(this.mailSender).createMimeMessage();
    }

}
