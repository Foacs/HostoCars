package fr.foacs.hostocars.controller;

import fr.foacs.hostocars.configuration.Loggable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import java.io.File;
import java.net.URI;
import java.util.Map;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * REST controller for e-mails.
 */
@Configuration
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mails")
@PropertySource("classpath:mail.properties")
@ConditionalOnProperty("spring.profiles.active")
@Tags(@Tag(name = "Mails", description = "Services related to mails."))
public class MailController {

    @NonNull
    @Value("${logging.file.name}")
    private String loggingFileName;

    @NonNull
    @Value("${mail.bearer.token}")
    private String mailBearerToken;

    @NonNull
    @Value("${mail.service.uri}")
    private String mailServiceUri;

    private final RestTemplate restTemplate;

    /**
     * Valued autowired constructor.
     *
     * @param restTemplate
     *     The autowired {@link RestTemplate} component
     */
    @Autowired
    public MailController(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Sends a mail with the given details and attach the log file to it.
     *
     * @param details
     *     The details of the mail to send
     *
     * @return an HTTP response
     */
    @Loggable
    @PutMapping
    @SneakyThrows(Exception.class)
    @Operation(summary = "Sends a mail.", description = "Sends a mail with the given details and the log file.",
        responses = @ApiResponse(description = "The mail has been sent successfully.", responseCode = "204"))
    public ResponseEntity<String> sendMail(@Parameter(required = true) @RequestBody @NonNull final Map<String, String> details) {
        // Writes the details as JSON
        final var detailsAsJson = new JSONObject();
        for (final var detail : details.entrySet()) {
            detailsAsJson.put(detail.getKey(), detail.getValue());
        }

        // Creates the body
        final var body = new LinkedMultiValueMap<String, Object>();
        body.add("details", detailsAsJson.toString());
        body.add("logs", new FileSystemResource(new File(this.loggingFileName)));

        // Create the headers
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(this.mailBearerToken);

        // Creates the request
        final var requestEntity = new HttpEntity<>(body, headers);

        // Calls the mail service
        final var responseEntity = this.restTemplate.postForEntity(URI.create(this.mailServiceUri), requestEntity, String.class);

        // Builds the response entity based on the service response
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

}
