package fr.vulture.hostocars.error;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Response data structure to return in case of error.
 */
@Data
public class ResponseData {

    @NotNull
    private String message;
    @NotNull
    private String timestamp;

    /**
     * Valued constructor with message. The timestamp is set to the current date.
     *
     * @param message
     *     The error message
     */
    public ResponseData(final String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
