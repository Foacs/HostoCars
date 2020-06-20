package fr.vulture.hostocars.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * DTO for the {@code Operations} table.
 */
@Data
public class Operation implements Serializable {

    private static final long serialVersionUID = -7842580553677158771L;

    private Integer id;
    private Integer interventionId;
    private String label;

    private List<OperationLine> operationLineList;

}
