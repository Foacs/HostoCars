package fr.vulture.hostocars.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Entity for the {@code Consumables} table.
 */
@Entity
@Data
@Table(name = "Consumables")
public class ConsumableEntity implements Serializable {

    private static final long serialVersionUID = 1706365274607343026L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id",
        unique = true,
        nullable = false,
        insertable = false,
        updatable = false,
        columnDefinition = "INTEGER")
    private Integer id;

    @Column(name = "interventionId",
        columnDefinition = "INTEGER")
    private Integer interventionId;

    @Column(name = "denomination",
        nullable = false,
        columnDefinition = "TEXT")
    private String denomination;

    @Column(name = "quantity",
        columnDefinition = "TEXT")
    private String quantity;

}
