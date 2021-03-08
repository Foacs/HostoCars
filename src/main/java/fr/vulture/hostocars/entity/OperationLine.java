package fr.vulture.hostocars.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * Entity for the {@code operationLines} table.
 */
@Data
@ToString
@javax.persistence.Entity
@Table(name = "operationLines")
@EqualsAndHashCode(callSuper = true)
public class OperationLine extends Entity {

    private static final long serialVersionUID = 7694570052168923210L;

    @Column(name = "type", nullable = false, columnDefinition = "TEXT")
    private String type;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "done", nullable = false, columnDefinition = "INTEGER")
    private Boolean done;

    @Exclude
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operationId", referencedColumnName = "id")
    private Operation operation;

}
