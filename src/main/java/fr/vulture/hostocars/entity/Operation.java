package fr.vulture.hostocars.entity;

import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Set;
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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;

/**
 * Entity for the {@code operations} table.
 */
@Entity
@Data
@Table(name = "operations")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
class Operation implements Serializable {

    private static final long serialVersionUID = -6271290610333034638L;

    @Id
    @Include
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
    @OneToMany(mappedBy = "operation", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<OperationLine> operationLines;

}
