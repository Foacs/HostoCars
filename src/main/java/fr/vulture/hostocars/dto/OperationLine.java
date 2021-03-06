package fr.vulture.hostocars.dto;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * DTO for the {@code operationLines} table.
 */
@Data
@ToString
public class OperationLine implements Serializable {

    private static final long serialVersionUID = 7694570052168923210L;

    private Integer id;

    private String type;

    private String description;

    private Boolean done;

    @Exclude
    @JsonBackReference
    private Operation operation;

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

        final OperationLine that = (OperationLine) obj;
        return nonNull(this.id) && this.id.equals(that.id);
    }

}
