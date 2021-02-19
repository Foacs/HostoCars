package fr.vulture.hostocars.entity;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.HashSet;
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
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * Entity for the {@code operations} table.
 */
@Data
@Entity
@ToString
@Table(name = "operations")
class Operation implements Serializable {

    private static final long serialVersionUID = -6271290610333034638L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false, columnDefinition = "INTEGER")
    private Integer id;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (isNull(obj) || this.getClass() != obj.getClass()) {
            return false;
        }

        final Operation that = (Operation) obj;
        return nonNull(this.id) && this.id.equals(that.id);
    }

}
