package fr.vulture.hostocars.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test class for the {@link MailController} class.
 */
@DisplayName("Mail controller")
@ExtendWith(MockitoExtension.class)
class MailControllerTest {

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Mock
    private JavaMailSender sender;

    @Mock
    private ControllerHelper helper;

    @InjectMocks
    private MailController mailController;

    /**
     * Initialization method called before each test.
     */
    @BeforeEach
    @SneakyThrows
    void initialize() {
        this.folder.create();
        ReflectionTestUtils.setField(this.mailController, "mailUsername", "mailUsername");
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
    @DisplayName("Send mail")
    final void testSendMail() {
        // Prepares the inputs
        final String recipient = "localPart@domain";
        final String subject = "subject";
        final String content = "content";
        final String pathToFirstAttachment = "pathToFirstAttachment";
        final String pathToSecondAttachment = "pathToSecondAttachment";
        final Mail mail = new Mail();
        mail.setRecipient(recipient);
        mail.setSubject(subject);
        mail.setContent(content);
        mail.setAttachmentArray(new String[] {pathToFirstAttachment, pathToSecondAttachment});

        // Prepares the intermediary results
        final MimeMessage message = new MimeMessage((Session) null);
        final ResponseEntity<?> response = mock(ResponseEntity.class);

        // Prepares the rules
        this.folder.newFile(pathToFirstAttachment);
        this.folder.newFile(pathToSecondAttachment);

        // Mocks the calls
        when(this.sender.createMimeMessage()).thenReturn(message);
        when(this.helper.resolvePutResponse(any(Runnable.class))).thenReturn(response);

        // Calls the method
        final ResponseEntity<?> result = this.mailController.sendMail(mail);

        // Checks the mocks calls
        verify(this.sender).createMimeMessage();
        verify(this.helper).resolvePutResponse(any(Runnable.class));

        // Checks the result
        assertSame(response, result, "Result different from expected");
        assertEquals("mailUsername", message.getFrom()[0].toString(), "Expeditor different from expected");
        assertEquals(recipient, message.getAllRecipients()[0].toString(), "Recipient different from expected");
        assertEquals(subject, message.getSubject(), "Subject different from expected");
        assertDoesNotThrow(message::getContent, "Content unexpectedly null");
    }

    /**
     * Tests the {@link MailController#sendMail} method in error.
     */
    @Test
    @SneakyThrows
    @DisplayName("Send mail (error case)")
    final void testSendMailInError() {
        // Prepares the inputs
        final String recipient = "localPart@domain";
        final String subject = "subject";
        final String content = "content";
        final String pathToFirstAttachment = "pathToFirstAttachment";
        final String pathToSecondAttachment = "pathToSecondAttachment";
        final Mail mail = new Mail();
        mail.setRecipient(recipient);
        mail.setSubject(subject);
        mail.setContent(content);
        mail.setAttachmentArray(new String[] {pathToFirstAttachment, pathToSecondAttachment});

        // Prepares the intermediary results
        final MimeMessage message = new MimeMessage((Session) null);

        // Prepares the rules
        this.folder.newFile(pathToFirstAttachment);
        this.folder.newFile(pathToSecondAttachment);

        // Mocks the calls
        when(this.sender.createMimeMessage()).thenReturn(message);
        when(this.helper.resolvePutResponse(any(Runnable.class))).thenThrow(new RuntimeException());

        // Calls the method
        assertThrows(RuntimeException.class, () -> this.mailController.sendMail(mail));

        // Checks the mocks calls
        verify(this.sender).createMimeMessage();
        verify(this.helper).resolvePutResponse(any(Runnable.class));

        // Checks the result
        assertEquals("mailUsername", message.getFrom()[0].toString(), "Expeditor different from expected");
        assertEquals(recipient, message.getAllRecipients()[0].toString(), "Recipient different from expected");
        assertEquals(subject, message.getSubject(), "Subject different from expected");
        assertDoesNotThrow(message::getContent, "Content unexpectedly null");
    }

}
