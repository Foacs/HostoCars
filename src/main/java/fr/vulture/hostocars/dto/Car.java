package fr.vulture.hostocars.dto;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * DTO for the {@code cars} table.
 */
@Data
@ToString
public class Car implements Serializable {

    private static final long serialVersionUID = -8531072274006990095L;

    private Integer id;

    private String registration;

    private String serialNumber;

    private String owner;

    private String brand;

    private String model;

    private String motorization;

    private String engineCode;

    private String releaseDate;

    private String comments;

    @Exclude
    private byte[] certificate;

    @Exclude
    private byte[] picture;

    @JsonManagedReference
    private Set<Intervention> interventions = new HashSet<>(0);

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

        final Car that = (Car) obj;
        return nonNull(this.id) && this.id.equals(that.id);
    }

}
