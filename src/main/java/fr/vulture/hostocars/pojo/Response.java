package fr.vulture.hostocars.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import lombok.Value;

/**
 * Representation of a response to send in case of error.
 */
@Value(staticConstructor = "from")
public class Response implements Serializable {

    private static final long serialVersionUID = 939456732837818264L;

    @NonNull
    private final String message;

    @NonNull
    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

}
