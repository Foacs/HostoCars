package fr.vulture.hostocars.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * Representation of a mail.
 */
@Data
public class Mail implements Serializable {

    private static final long serialVersionUID = -7124304390294101733L;

    private String recipient;
    private String subject;
    private String content;
    private String[] attachmentArray;

}
