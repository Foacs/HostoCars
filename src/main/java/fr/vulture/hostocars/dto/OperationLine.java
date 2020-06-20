package fr.vulture.hostocars.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * DTO for the {@code OperationLine} table.
 */
@Data
public class OperationLine implements Serializable {

    private static final long serialVersionUID = 930402422635946493L;

    private Integer id;
    private Integer operationId;
    private String type;
    private String description;
    private Boolean done;

}
