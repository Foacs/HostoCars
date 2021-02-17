package fr.vulture.hostocars.entity;

import static java.util.Objects.nonNull;
import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * Entity for the {@code operations} table.
 */
@Entity
@Data
@Table(name = "operations")
class Operation implements Serializable {

    private static final long serialVersionUID = -6271290610333034638L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false, columnDefinition = "INTEGER")
    private Integer id;

    @Column(name = "label", nullable = false, columnDefinition = "TEXT")
    private String label;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "interventionId", referencedColumnName = "id")
    private Intervention intervention;

    @JsonManagedReference
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "operation", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<OperationLine> operationLines = new ArrayList<>(0);

    /**
     * Sets the operation lines.
     *
     * @param operationLines
     *     The operation lines to set
     */
    public void setInterventions(final List<OperationLine> operationLines) {
        this.operationLines.clear();

        if (nonNull(operationLines)) {
            this.operationLines.addAll(operationLines);
        }
    }

}
