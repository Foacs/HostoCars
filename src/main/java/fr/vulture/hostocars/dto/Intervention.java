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
 * DTO for the {@code interventions} table.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Intervention extends Dto {

    private static final long serialVersionUID = 5483194654964373407L;

    private Integer year;

    private Integer number;

    private String status;

    private String description;

    private Integer mileage;

    private Double estimatedTime;

    private Double realTime;

    private Double amount;

    private Double paidAmount;

    private String comments;

    @Exclude
    @JsonBackReference
    private Car car;

    @JsonManagedReference
    private Set<Operation> operations = new HashSet<>(0);

}
