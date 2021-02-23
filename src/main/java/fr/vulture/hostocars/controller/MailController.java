package fr.vulture.hostocars.controller;

import static java.util.Objects.nonNull;

import fr.vulture.hostocars.configuration.Loggable;
import fr.vulture.hostocars.pojo.Mail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import java.io.File;
import javax.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for e-mails.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mails")
@Tags(@Tag(name = "Mails", description = "Services related to mails."))
public class MailController {

    @NonNull
    private final JavaMailSender sender;

    @NonNull
    private final ControllerHelper helper;

    @NonNull
    @Value("${spring.mail.username}")
    private String mailUsername;

    /**
     * Valued autowired constructor.
     *
     * @param sender
     *     The autowired {@link JavaMailSender} component
     * @param helper
     *     The autowired {@link ControllerHelper} component
     */
    @Autowired
    public MailController(final JavaMailSender sender, final ControllerHelper helper) {
        this.sender = sender;
        this.helper = helper;
    }

    /**
     * Sends a {@link Mail} generated from the REST call body.
     *
     * @param mail
     *     The {@link Mail} to send
     *
     * @return an HTTP response
     */
    @Loggable
    @PutMapping
    @SneakyThrows
    @Operation(summary = "Sends a mail.", description = "Sends a mail using the mail address configured in the application properties. Some attachments paths can also be added.",
        responses = @ApiResponse(description = "The mail has been sent successfully.", responseCode = "204"))
    public ResponseEntity<?> sendMail(@Parameter(required = true) @RequestBody @NonNull final Mail mail) {
        // Creates a message
        final MimeMessage message = this.sender.createMimeMessage();

        // Instantiates a message helper from the message
        final MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

        // Sets the message fields
        messageHelper.setFrom(this.mailUsername);
        messageHelper.setTo(mail.getRecipient());
        messageHelper.setSubject(mail.getSubject());
        messageHelper.setText(mail.getContent(), true);

        // If there are attachments, attaches them
        final String[] attachmentArray = mail.getAttachmentArray();
        if (nonNull(attachmentArray)) {
            for (final String pathToAttachment : attachmentArray) {
                // Gets the attachment file resource
                final Resource file = new FileSystemResource(new File(pathToAttachment));

                // Adds the attachment file
                messageHelper.addAttachment(file.getFilename(), file);
            }
        }

        // Sends the mail
        return this.helper.resolvePutResponse(() -> this.sender.send(message));
    }

}
