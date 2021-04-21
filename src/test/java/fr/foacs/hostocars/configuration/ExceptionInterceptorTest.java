package fr.foacs.hostocars.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/**
 * Test class for the {@link ExceptionInterceptor} class.
 */
@DisplayName("Exception interceptor")
class ExceptionInterceptorTest {

    private final ExceptionInterceptor interceptor = new ExceptionInterceptor();

    /**
     * Tests the {@link ExceptionInterceptor#resolve} method without message.
     */
    @Test
    @DisplayName("Resolve (without message)")
    void testResolveWithoutMessage() {
        // Calls the method
        final var result = this.interceptor.resolve(new Exception());

        // Checks the result
        assertNotNull(result, "Result object unexpectedly null");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode(), "Response status different from expected");
        assertEquals("Exception", result.getBody(), "Response body different from expected");
    }

    /**
     * Tests the {@link ExceptionInterceptor#resolve} method with a message.
     */
    @Test
    @DisplayName("Resolve (with message)")
    void testResolveWithMessage() {
        // Calls the method
        final var result = this.interceptor.resolve(new Exception("message"));

        // Checks the result
        assertNotNull(result, "Result object unexpectedly null");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode(), "Response status different from expected");
        assertEquals("Exception: message", result.getBody(), "Response body different from expected");
    }

}
