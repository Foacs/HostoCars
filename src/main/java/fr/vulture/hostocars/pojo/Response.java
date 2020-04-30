package fr.vulture.hostocars.pojo;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.NonNull;
import lombok.Value;

/**
 * Representation of a response to send in case of error.
 */
@Value(staticConstructor = "from")
public class Response implements Serializable {

    private static final long serialVersionUID = 939456732837818264L;

    @NonNull String message;

    @NonNull String timestamp = LocalDateTime.now().format(ISO_LOCAL_DATE_TIME);

}
