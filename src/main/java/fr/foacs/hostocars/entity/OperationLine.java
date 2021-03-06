package fr.foacs.hostocars.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fr.foacs.hostocars.configuration.Hide;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity for the {@code operationLines} table.
 */
@Entity
@Getter
@Setter
@Table(name = "operationLines")
class OperationLine extends AbstractEntity {

    private static final long serialVersionUID = 7694570052168923210L;

    @Column(name = "type", nullable = false, columnDefinition = "TEXT")
    private String type;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "done", nullable = false, columnDefinition = "INTEGER")
    private Boolean done;

    @Hide
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operationId", referencedColumnName = "id")
    private Operation operation;

}
