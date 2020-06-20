package fr.vulture.hostocars.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

/**
 * Entity for the {@code Cars} table.
 */
@Entity
@Data
@Table(name = "Interventions", uniqueConstraints = @UniqueConstraint(columnNames = {"creationYear", "number"}))
public class InterventionEntity implements Serializable {

    private static final long serialVersionUID = -9130046034547531677L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false, columnDefinition = "INTEGER")
    private Integer id;

    @Column(name = "carId", nullable = false, columnDefinition = "INTEGER")
    private Integer carId;

    @Column(name = "creationYear", nullable = false, columnDefinition = "INTEGER")
    private Integer creationYear;

    @Column(name = "number", nullable = false, columnDefinition = "INTEGER")
    private Integer number;

    @Column(name = "status", nullable = false, columnDefinition = "TEXT")
    private String status;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "mileage", columnDefinition = "INTEGER")
    private Integer mileage;

    @Column(name = "estimatedTime", columnDefinition = "REAL")
    private Double estimatedTime;

    @Column(name = "realTime", columnDefinition = "REAL")
    private Double realTime;

    @Column(name = "amount", columnDefinition = "REAL")
    private Double amount;

    @Column(name = "paidAmount", columnDefinition = "REAL")
    private Double paidAmount;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

}
