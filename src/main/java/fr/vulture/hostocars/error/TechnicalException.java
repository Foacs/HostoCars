package fr.vulture.hostocars.error;

import java.text.MessageFormat;

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
    public TechnicalException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

}
