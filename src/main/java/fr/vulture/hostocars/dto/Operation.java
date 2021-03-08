package fr.vulture.hostocars.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * DTO for the {@code operations} table.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Operation extends Dto {

    private static final long serialVersionUID = 7380315381960933148L;

    private String label;

    @Exclude
    @JsonBackReference
    private Intervention intervention;

    @JsonManagedReference
    private Set<OperationLine> operationLines = new HashSet<>(0);

}
