package fr.vulture.hostocars.dto;

import static java.util.Objects.hash;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * Abstract representation of a DTO.
 */
@Data
@ToString
abstract class Dto implements Serializable {

    private static final long serialVersionUID = -5578079657503669784L;

    private Integer id;

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return hash(this.id);
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

        final Dto that = (Dto) obj;
        return nonNull(this.id) && this.id.equals(that.id);
    }

}
