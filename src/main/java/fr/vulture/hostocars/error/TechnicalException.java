package fr.vulture.hostocars.error;

import javax.validation.constraints.NotNull;
import org.slf4j.helpers.MessageFormatter;

/**
 * Technical exception class for the application.
 */
public class TechnicalException extends Exception {

    /**
     * Constructor with a message and its arguments.
     *
     * @param message
     *     The message
     * @param args
     *     The messages arguments
     */
    public TechnicalException(@NotNull final String message, @NotNull final Object... args) {
        super(MessageFormatter.format(message, args).getMessage());
    }

}
