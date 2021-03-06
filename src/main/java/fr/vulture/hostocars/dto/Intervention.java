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
 * DTO for the {@code interventions} table.
 */
@Data
@ToString
public class Intervention implements Serializable {

    private static final long serialVersionUID = -9130046034547531677L;

    private Integer id;

    private Integer year;

    private Integer number;

    private String status;

    private String description;

    private Integer mileage;

    private Double estimatedTime;

    private Double realTime;

    private Double amount;

    private Double paidAmount;

    private String comments;

    @Exclude
    @JsonBackReference
    private Car car;

    @JsonManagedReference
    private Set<Operation> operations = new HashSet<>(0);

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

        final Intervention that = (Intervention) obj;
        return nonNull(this.id) && this.id.equals(that.id);
    }

}
