package fr.vulture.hostocars.exception;

import javax.validation.constraints.NotNull;
import org.slf4j.helpers.MessageFormatter;

/**
 * Functional exception class for the application.
 */
public class FunctionalException extends Exception {

    /**
     * Constructor with a message and its arguments.
     *
     * @param message
     *     The message
     * @param args
     *     The messages arguments
     */
    public FunctionalException(@NotNull final String message, @NotNull final Object... args) {
        super(MessageFormatter.arrayFormat(message, args).getMessage());
    }

}
