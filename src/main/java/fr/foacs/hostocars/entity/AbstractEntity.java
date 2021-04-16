package fr.foacs.hostocars.entity;

import static java.util.Objects.hash;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract representation of a DTO.
 */
@Getter
@Setter
@MappedSuperclass
abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 6385609581190828938L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false, columnDefinition = "INTEGER")
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

        final AbstractEntity that = (AbstractEntity) obj;
        return nonNull(this.id) && this.id.equals(that.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.valueOf(this.id);
    }

}
