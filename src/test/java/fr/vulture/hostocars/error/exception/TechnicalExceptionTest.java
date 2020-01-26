package fr.vulture.hostocars.error.exception;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link TechnicalException} class.
 */
@DisplayName("Technical exception")
class TechnicalExceptionTest {

    /**
     * Tests the {@link TechnicalException#TechnicalException(String, Object...)} constructor without arguments.
     */
    @Test
    @DisplayName("Constructing a technical exception with a message")
    final void testConstructorWithMessage() {
        final String message = "message";

        final TechnicalException result = new TechnicalException(message);
        assertAll("Asserting result object",
            () -> assertEquals(message, result.getMessage(), "Result message different from expected"),
            () -> assertNull(result.getCause(), "Result cause unexpectedly not null")
        );
    }

    /**
     * Tests the {@link TechnicalException#TechnicalException(String, Object...)} constructor with arguments.
     */
    @Test
    @DisplayName("Constructing a technical exception with a message and arguments")
    final void testConstructorWithMessageAndArguments() {
        final String message = "message with args : {}, {}, {}";
        final String arg1 = "arg1", arg2 = "arg2", arg3 = "arg3";

        final String expectedResult = "message with args : arg1, arg2, arg3";

        final TechnicalException result = new TechnicalException(message, arg1, arg2, arg3);
        assertAll("Asserting result object",
            () -> assertEquals(expectedResult, result.getMessage(), "Result message different from expected"),
            () -> assertNull(result.getCause(), "Result cause unexpectedly not null")
        );
    }

    /**
     * Tests the {@link TechnicalException#TechnicalException(Throwable, String, Object...)} constructor without arguments.
     */
    @Test
    @DisplayName("Constructing a technical exception with a cause and a message")
    final void testConstructorWithCauseAndMessage() {
        final Throwable cause = new Throwable();
        final String message = "message";

        final TechnicalException result = new TechnicalException(cause, message);
        assertAll("Asserting result object",
            () -> assertEquals(message, result.getMessage(), "Result message different from expected"),
            () -> assertEquals(cause, result.getCause(), "Result cause different from expected")
        );
    }

    /**
     * Tests the {@link TechnicalException#TechnicalException(Throwable, String, Object...)} constructor with arguments.
     */
    @Test
    @DisplayName("Constructing a technical exception with a cause, a message and arguments")
    final void testConstructorWithCauseAndMessageAndArguments() {
        final Throwable cause = new Throwable();
        final String message = "message with args : {}, {}, {}";
        final String arg1 = "arg1", arg2 = "arg2", arg3 = "arg3";

        final String expectedResult = "message with args : arg1, arg2, arg3";

        final TechnicalException result = new TechnicalException(cause, message, arg1, arg2, arg3);
        assertAll("Asserting result object",
            () -> assertEquals(expectedResult, result.getMessage(), "Result message different from expected"),
            () -> assertEquals(cause, result.getCause(), "Result cause different from expected")
        );
    }

}
