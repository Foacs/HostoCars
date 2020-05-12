package fr.vulture.hostocars.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * DTO for the {@code Consumables} table.
 */
@Data
public class Consumable implements Serializable {

    private static final long serialVersionUID = -5104174406157721821L;

    private Integer id;
    private Integer interventionId;
    private String denomination;
    private String quantity;

}
