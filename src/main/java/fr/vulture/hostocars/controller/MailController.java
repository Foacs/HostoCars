package fr.vulture.hostocars.controller;

import static fr.vulture.hostocars.utils.ControllerUtils.INTERNAL_SERVER_ERROR_CODE;
import static fr.vulture.hostocars.utils.ControllerUtils.OK_CODE;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import fr.vulture.hostocars.pojo.Mail;
import fr.vulture.hostocars.pojo.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import java.io.File;
import java.util.Objects;
import java.util.stream.Stream;
import javax.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mail")
@Tags(@Tag(name = "Mails", description = "Services related to mails."))
public class MailController {

    private final JavaMailSender sender;

    /**
     * Valued autowired constructor.
     *
     * @param sender
     *     The autowired {@link JavaMailSender} component
     */
    @Autowired
    public MailController(final JavaMailSender sender) {
        this.sender = sender;
    }

    /**
     * Sends the given {@link Mail}.
     *
     * @param mail
     *     The {@link Mail} to send
     *
     * @return an HTTP response
     */
    @PostMapping("/send")
    @Operation(summary = "Sends a mail.", description = "Sends a mail using the 'hostocars@gmail.com' mail address. Some attachments can be added.",
        responses = {@ApiResponse(description = "The mail has been sent successfully.", responseCode = OK_CODE, content = @Content),
            @ApiResponse(description = "An error has occurred.", responseCode = INTERNAL_SERVER_ERROR_CODE,
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))})
    public ResponseEntity<?> sendMail(@Parameter(required = true) @RequestBody @NonNull final Mail mail) {
        log.info("[sendMail <= Calling] With body = {}", mail);

        try {
            // Gets the message fields
            final String recipient = mail.getRecipient();
            final String subject = mail.getSubject();
            final String content = mail.getContent();

            // If any mandatory field is missing from the body, throws an exception
            assert Stream.of(recipient, subject, content).noneMatch(Objects::isNull) : "Missing mandatory field(s)";

            // Creates a message
            final MimeMessage message = this.sender.createMimeMessage();

            // Instantiates a message helper from the message
            final MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Sets the message fields
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content, true);

            // If there are attachments, attaches them
            final String[] attachmentArray = mail.getAttachmentArray();
            if (nonNull(attachmentArray)) {
                for (final String pathToAttachment : attachmentArray) {
                    // Gets the attachment file resource
                    final Resource file = new FileSystemResource(new File(pathToAttachment));

                    // Adds the attachment file
                    helper.addAttachment(requireNonNull(file.getFilename()), file);
                }
            }

            // Sends the mail
            this.sender.send(message);

            // Returns a 200 status
            log.info("[sendMail => {}]", OK.value());
            return ResponseEntity.ok().build();
        }

        // If an unknown throwable has been thrown, returns a 500 status
        catch (final Throwable e) {
            log.error("[sendMail => {}]", INTERNAL_SERVER_ERROR.value(), e);
            final String responseMessage = e.getClass().getSimpleName();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Response.from(responseMessage));
        }
    }

}
