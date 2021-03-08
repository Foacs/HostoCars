package fr.vulture.hostocars.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * DTO for the {@code operationLines} table.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OperationLine extends Dto {

    private static final long serialVersionUID = 730947699648700461L;

    private String type;

    private String description;

    private Boolean done;

    @Exclude
    @JsonBackReference
    private Operation operation;

}
