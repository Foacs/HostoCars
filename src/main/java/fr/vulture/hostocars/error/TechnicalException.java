package fr.vulture.hostocars.error;

/**
 * Technical exception class for the application
 */
public class TechnicalException extends Exception {

    /**
     * Constructor with message.
     *
     * @param message
     *     The message
     */
    public TechnicalException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message
     *     The message
     * @param cause
     *     The cause
     */
    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

}
