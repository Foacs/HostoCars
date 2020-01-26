package fr.vulture.hostocars.mail.controller;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

import fr.vulture.hostocars.error.ResponseData;
import fr.vulture.hostocars.error.exception.FunctionalException;
import java.io.File;
import java.util.List;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for e-mails.
 */
@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = "*")
public class MailController {

    private static final Logger logger = LoggerFactory.getLogger(MailController.class);

    private static final String DEFAULT_FILENAME = "file";

    private final JavaMailSender sender;

    /**
     * Valued constructor.
     *
     * @param sender
     *     The autowired {@link JavaMailSender} component
     */
    @Autowired
    public MailController(final JavaMailSender sender) {
        this.sender = sender;
    }

    /**
     * Sends an e-mail generated from the input body.
     *
     * @param body
     *     The body
     *
     * @return an HTTP response
     */
    @PostMapping("/send")
    public final ResponseEntity<?> sendMail(@RequestBody @NotNull final MailRequestBody body) {
        logger.info("[sendMail <= Calling] With body = {}", body);

        try {
            final String recipient = body.getRecipient();
            final String subject = body.getSubject();
            final String content = body.getContent();

            // If any mandatory field is missing from the body, throws an exception
            if (isNull(recipient) || isNull(subject) || isNull(content)) {
                throw new FunctionalException("Missing mandatory field(s)");
            }

            // Creates a message
            final MimeMessage message = this.sender.createMimeMessage();

            // Instantiates a message helper from the message
            final MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Sets the message fields
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content, true);

            // If there are attachments, attaches them
            final List<String> attachmentPaths = body.getAttachments();
            if (nonNull(attachmentPaths)) {
                for (final String pathToAttachment : attachmentPaths) {
                    // Gets the file resource
                    final Resource file = new FileSystemResource(new File(pathToAttachment));

                    // Determines the attachment name
                    final String filename = isNull(file.getFilename()) ? DEFAULT_FILENAME : file.getFilename();

                    // Attaches the attachment
                    helper.addAttachment(filename, file);
                }
            }

            // Sends the mail
            this.sender.send(message);

            // Returns a 200 status
            logger.info("[sendMail => {}]", OK.value());
            return ResponseEntity.ok().build();
        }

        // If an unknown exception has been thrown, returns a 500 status
        catch (final Exception e) {
            logger.error("[sendMail => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ResponseData(responseMessage));
        }
    }

    /**
     * Representation of a mail body with optional fields for web service requests.
     */
    @Data
    private static class MailRequestBody {

        private String recipient;
        private String subject;
        private String content;
        private List<String> attachments;

    }

}
