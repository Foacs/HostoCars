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
 * Entity for the {@code OperationLines} table.
 */
@Entity
@Data
@Table(name = "OperationLines")
public class OperationLineEntity implements Serializable {

    private static final long serialVersionUID = 7694570052168923210L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id",
        unique = true,
        nullable = false,
        insertable = false,
        updatable = false,
        columnDefinition = "INTEGER")
    private Integer id;

    @Column(name = "operationId",
        nullable = false,
        columnDefinition = "INTEGER")
    private Integer operationId;

    @Column(name = "description",
        nullable = false,
        columnDefinition = "TEXT")
    private String description;

    @Column(name = "done",
        nullable = false,
        columnDefinition = "INTEGER")
    private Boolean done;

}
