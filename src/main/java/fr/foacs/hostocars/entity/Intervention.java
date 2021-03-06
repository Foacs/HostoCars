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
 * Entity for the {@code interventions} table.
 */
@Entity
@Getter
@Setter
@Table(name = "interventions")
class Intervention extends AbstractEntity {

    private static final long serialVersionUID = -9130046034547531677L;

    @Column(name = "year", insertable = false, updatable = false, columnDefinition = "INTEGER")
    private Integer year;

    @Column(name = "number", insertable = false, updatable = false, columnDefinition = "INTEGER")
    private Integer number;

    @Column(name = "status", nullable = false, columnDefinition = "TEXT")
    private String status;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "mileage", columnDefinition = "INTEGER")
    private Integer mileage;

    @Column(name = "estimatedTime", columnDefinition = "REAL")
    private Double estimatedTime;

    @Column(name = "realTime", columnDefinition = "REAL")
    private Double realTime;

    @Column(name = "amount", columnDefinition = "REAL")
    private Double amount;

    @Column(name = "paidAmount", columnDefinition = "REAL")
    private Double paidAmount;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Hide
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "carId", referencedColumnName = "id")
    private Car car;

    @JsonManagedReference
    @OneToMany(mappedBy = "intervention", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Operation> operations = new HashSet<>(0);

}
