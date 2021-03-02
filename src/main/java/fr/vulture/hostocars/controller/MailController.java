package fr.vulture.hostocars.controller;

import fr.vulture.hostocars.configuration.Loggable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import java.io.File;
import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
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
@Tags(@Tag(name = "Mails", description = "Services related to mails."))
public class MailController {

    @NonNull
    @Value("${logging.file.name}")
    private String logFilePath;

    @NonNull
    @Value("${mail.bearer.token}")
    private String mailBearerToken;

    @NonNull
    @Value("${mail.service.uri}")
    private String mailServiceUri;

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
    @SneakyThrows
    @Operation(summary = "Sends a mail.", description = "Sends a mail with the given details and the log file.",
        responses = @ApiResponse(description = "The mail has been sent successfully.", responseCode = "204"))
    public ResponseEntity<?> sendMail(@Parameter(required = true) @RequestBody @NonNull final Map<String, String> details) {
        // Writes the details as JSON
        final JSONObject detailsAsJson = new JSONObject();
        for (final Entry<String, String> detail : details.entrySet()) {
            detailsAsJson.put(detail.getKey(), detail.getValue());
        }

        // Creates the body
        final LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("details", detailsAsJson.toString());
        body.add("logs", new FileSystemResource(new File(this.logFilePath)));

        // Create the headers
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(this.mailBearerToken);

        // Creates the request
        final HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Calls the mail service
        final ResponseEntity<String> responseEntity = new RestTemplate().postForEntity(URI.create(this.mailServiceUri), requestEntity, String.class);

        // Builds the response entity based on the service response
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

}
