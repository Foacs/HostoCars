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
 * Entity for the {@code Operations} table.
 */
@Entity
@Data
@Table(name = "Operations")
public class OperationEntity implements Serializable {

    private static final long serialVersionUID = -6271290610333034638L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false, columnDefinition = "INTEGER")
    private Integer id;

    @Column(name = "interventionId", nullable = false, columnDefinition = "INTEGER")
    private Integer interventionId;

    @Column(name = "label", nullable = false, columnDefinition = "TEXT")
    private String label;

}
