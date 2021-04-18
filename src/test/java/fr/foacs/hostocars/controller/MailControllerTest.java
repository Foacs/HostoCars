package fr.foacs.hostocars.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.TemporaryFolder;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

/**
 * Test class for the {@link MailController} class.
 */
@DisplayName("Mail controller")
@ExtendWith(MockitoExtension.class)
class MailControllerTest {

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Mock
    private RestTemplate restTemplate;

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
     * Demolition method called after each test.
     */
    @AfterEach
    void teardown() {
        this.folder.delete();
    }

    /**
     * Tests the {@link MailController#sendMail(Map)} method with a null map of details.
     */
    @Test
    @DisplayName("Send mail (null map of details)")
    void testSendMailWithNullMapOfDetails() {
        // Calls the method
        assertThrows(NullPointerException.class, () -> this.mailController.sendMail(null));
    }

    /**
     * Tests the {@link MailController#sendMail(Map)} method.
     */
    @Test
    @DisplayName("Send mail")
    @SneakyThrows(IOException.class)
    void testSendMail() {
        // Sets the controller properties
        final String loggingFileName = "loggingFileName";
        ReflectionTestUtils.setField(this.mailController, "loggingFileName", loggingFileName);
        final String mailServiceUri = "mailServiceUri";
        ReflectionTestUtils.setField(this.mailController, "mailServiceUri", mailServiceUri);

        // Mocks the log file
        this.folder.newFile(loggingFileName);

        // Prepares the inputs
        final Map<String, String> details = new HashMap<>(2);
        details.put("key1", "value1");
        details.put("key2", "value2");

        // Prepares the intermediary results
        final ResponseEntity<String> responseEntity = mock(ResponseEntity.class);
        final HttpStatus statusCode = mock(HttpStatus.class);
        final String body = "body";

        // Mocks the calls
        doReturn(responseEntity).when(this.restTemplate).postForEntity(ArgumentMatchers.any(URI.class), ArgumentMatchers.any(Object.class), ArgumentMatchers.any(Class.class));
        doReturn(statusCode).when(responseEntity).getStatusCode();
        doReturn(body).when(responseEntity).getBody();

        // Calls the method
        final ResponseEntity<String> result = this.mailController.sendMail(details);

        // Initializes the argument captors
        final ArgumentCaptor<URI> uriCaptor = forClass(URI.class);
        final ArgumentCaptor<Object> requestCaptor = forClass(Object.class);
        final ArgumentCaptor<Class> responseTypeCaptor = forClass(Class.class);

        // Checks the mocks calls
        verify(this.restTemplate).postForEntity(uriCaptor.capture(), requestCaptor.capture(), responseTypeCaptor.capture());
        verify(responseEntity).getStatusCode();
        verify(responseEntity).getBody();

        // Gets the captured arguments
        final URI capturedUri = uriCaptor.getValue();
        final Object capturedRequest = requestCaptor.getValue();
        final Class capturedResponseType = responseTypeCaptor.getValue();

        // Checks the result and captured arguments
        assertNotNull(result, "Result response unexpectedly null");
        assertAll("Captured argument(s) unexpectedly null", () -> assertNotNull(capturedUri, "Captured URI unexpectedly null"),
            () -> assertNotNull(capturedRequest, "Captured request unexpectedly null"), () -> assertNotNull(capturedResponseType, "Captured response type unexpectedly null"));
        assertAll("Result different from expected", () -> assertEquals(statusCode, result.getStatusCode(), "Result status code different from expected"),
            () -> assertEquals(body, result.getBody(), "Result body different from expected"));
        assertAll("Captured argument(s) different from expected", () -> assertEquals(mailServiceUri, capturedUri.toString(), "Captured URI different from expected"),
            () -> assertSame(String.class, capturedResponseType, "Captured response type different from expected"));
    }

}
