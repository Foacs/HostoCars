package fr.vulture.hostocars.error;

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
    public FunctionalException(String message, Object... args) {
        super(MessageFormatter.format(message, args).getMessage());
    }

}
