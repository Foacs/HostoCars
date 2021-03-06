package fr.foacs.hostocars.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.foacs.hostocars.configuration.Hide;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity for the {@code operations} table.
 */
@Entity
@Getter
@Setter
@Table(name = "operations")
class Operation extends AbstractEntity {

    private static final long serialVersionUID = -6271290610333034638L;

    @Column(name = "label", nullable = false, columnDefinition = "TEXT")
    private String label;

    @Hide
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "interventionId", referencedColumnName = "id")
    private Intervention intervention;

    @JsonManagedReference
    @OneToMany(mappedBy = "operation", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<OperationLine> operationLines = new HashSet<>(0);

}
