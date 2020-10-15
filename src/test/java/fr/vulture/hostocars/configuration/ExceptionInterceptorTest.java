package fr.vulture.hostocars.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test class for the {@link ExceptionInterceptor} class.
 */
@DisplayName("Exception interceptor")
@ExtendWith(MockitoExtension.class)
class ExceptionInterceptorTest {

    private final ExceptionInterceptor interceptor = new ExceptionInterceptor();

    /**
     * Tests the {@link ExceptionInterceptor#resolve} method without message.
     */
    @Test
    @DisplayName("Resolve without message")
    void testResolveWithoutMessage() {
        final ResponseEntity<String> result = this.interceptor.resolve(new Exception());

        assertNotNull(result, "Result object unexpectedly null");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode(), "Response status different from expected");
        assertEquals("Exception", result.getBody(), "Response body different from expected");
    }

    /**
     * Tests the {@link ExceptionInterceptor#resolve} method with a message.
     */
    @Test
    @DisplayName("Resolve with message")
    void testResolveWithMessage() {
        final ResponseEntity<String> result = this.interceptor.resolve(new Exception("message"));

        assertNotNull(result, "Result object unexpectedly null");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode(), "Response status different from expected");
        assertEquals("Exception: message", result.getBody(), "Response body different from expected");
    }

}
