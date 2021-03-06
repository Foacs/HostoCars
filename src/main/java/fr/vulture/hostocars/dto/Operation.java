package fr.vulture.hostocars.dto;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * DTO for the {@code operations} table.
 */
@Data
@ToString
public class Operation implements Serializable {

    private static final long serialVersionUID = -6271290610333034638L;

    private Integer id;

    private String label;

    @Exclude
    @JsonBackReference
    private Intervention intervention;

    @JsonManagedReference
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
