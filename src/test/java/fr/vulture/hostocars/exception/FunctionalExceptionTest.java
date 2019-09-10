package fr.vulture.hostocars.exception;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link FunctionalException} class.
 */
@DisplayName("Functional exception")
class FunctionalExceptionTest {

    /**
     * Tests the {@link FunctionalException#FunctionalException(String, Object...)} constructor without arguments.
     */
    @Test
    @DisplayName("Constructing a functional exception with a message")
    void testConstructorWithMessage() {
        final String message = "message";

        final FunctionalException result = new FunctionalException(message);
        assertAll("Asserting result object",
            () -> assertEquals(message, result.getMessage(), "Result message different from expected"),
            () -> assertNull(result.getCause(), "Result cause unexpectedly not null")
        );
    }

    /**
     * Tests the {@link FunctionalException#FunctionalException(String, Object...)} constructor with arguments.
     */
    @Test
    @DisplayName("Constructing a functional exception with a message and arguments")
    void testConstructorWithMessageAndArguments() {
        final String message = "message with args : {}, {}, {}";
        final String arg1 = "arg1", arg2 = "arg2", arg3 = "arg3";

        final String expectedResult = "message with args : arg1, arg2, arg3";

        final FunctionalException result = new FunctionalException(message, arg1, arg2, arg3);
        assertAll("Asserting result object",
            () -> assertEquals(expectedResult, result.getMessage(), "Result message different from expected"),
            () -> assertNull(result.getCause(), "Result cause unexpectedly not null")
        );
    }

}
