package fr.vulture.hostocars.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * Entity for the {@code operations} table.
 */
@Data
@Entity
@ToString
@Table(name = "operations")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
class Operation extends AbstractEntity {

    private static final long serialVersionUID = -6271290610333034638L;

    @Column(name = "label", nullable = false, columnDefinition = "TEXT")
    private String label;

    @Exclude
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "interventionId", referencedColumnName = "id")
    private Intervention intervention;

    @JsonManagedReference
    @OneToMany(mappedBy = "operation", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<OperationLine> operationLines = new HashSet<>(0);

}
