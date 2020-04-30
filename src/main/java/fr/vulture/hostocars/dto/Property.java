package fr.vulture.hostocars.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * DTO for the {@code DatabaseInfo} table.
 */
@Data
public class Property implements Serializable {

    private static final long serialVersionUID = -1294475368114372402L;

    private Integer id;
    private String key;
    private String value;

}
