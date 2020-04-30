package fr.vulture.hostocars.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

import fr.vulture.hostocars.pojo.Mail;
import fr.vulture.hostocars.pojo.Response;
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
import org.springframework.http.HttpStatus;
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
     * Tests the {@link MailController#sendMail} method with {@link HttpStatus#OK} status.
     */
    @Test
    @SneakyThrows
    @DisplayName("Sending a mail with an OK status")
    final void testSendMailWithOkStatus() {
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
        assertSame(OK, response.getStatusCode(), "Response status different from expected");
        assertNull(response.getBody(), "Response body unexpectedly not null");
        assertEquals(recipient, message.getAllRecipients()[0].toString(), "Recipient different from expected");
        assertEquals(subject, message.getSubject(), "Subject different from expected");
        assertDoesNotThrow(message::getContent, "Content unexpectedly null");

        verify(this.mailSender, times(1)).createMimeMessage();
        verify(this.mailSender, times(1)).send(message);
    }

    /**
     * Tests the {@link MailController#sendMail} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status and a null recipient.
     */
    @Test
    @SneakyThrows
    @DisplayName("Sending a mail with an OK status and a null recipient")
    final void testSendMailWithOkStatusAndNullRecipient() {
        final Mail mail = new Mail();
        final String subject = "subject";
        mail.setSubject(subject);
        final String content = "content";
        mail.setContent(content);

        final ResponseEntity<?> response = this.mailController.sendMail(mail);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertSame(AssertionError.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");
    }

    /**
     * Tests the {@link MailController#sendMail} method with {@link HttpStatus#INTERNAL_SERVER_ERROR} status.
     */
    @Test
    @DisplayName("Sending a mail with an INTERNAL_SERVER_ERROR status")
    final void testSendMailWithInternalError() {
        final Mail mail = new Mail();
        final String recipient = "localPart@domain";
        mail.setRecipient(recipient);
        final String subject = "subject";
        mail.setSubject(subject);
        final String content = "content";
        mail.setContent(content);

        when(this.mailSender.createMimeMessage()).thenThrow(new RuntimeException(""));

        final ResponseEntity<?> response = this.mailController.sendMail(mail);

        assertNotNull(response, "Response unexpectedly null");
        assertSame(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Response status different from expected");
        assertNotNull(response.getBody(), "Response body unexpectedly null");
        assertSame(Response.class, response.getBody().getClass(), "Response body class different from expected");
        assertSame(RuntimeException.class.getSimpleName(), ((Response) response.getBody()).getMessage(),
            "Response body message different from expected");

        verify(this.mailSender, times(1)).createMimeMessage();
    }

}
