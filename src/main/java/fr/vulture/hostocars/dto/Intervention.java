package fr.vulture.hostocars.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * DTO for the {@code Interventions} table.
 */
@Data
public class Intervention implements Serializable {

    private static final long serialVersionUID = -8415131826413085521L;

    private Integer id;
    private Integer carId;
    private Integer creationYear;
    private Integer number;
    private String status;
    private String description;
    private Integer mileage;
    private Double estimatedTime;
    private Double realTime;
    private Double amount;
    private Double paidAmount;

    private List<Operation> operationList;
    private List<Consumable> consumableList;

}
