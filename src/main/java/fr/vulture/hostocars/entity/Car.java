package fr.vulture.hostocars.entity;

import static java.util.Objects.nonNull;
import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString.Exclude;

/**
 * Entity for the {@code cars} table.
 */
@Entity
@Data
@Table(name = "cars")
public class Car implements Serializable {

    private static final long serialVersionUID = -8531072274006990095L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false, columnDefinition = "INTEGER")
    private Integer id;

    @Column(name = "registration", unique = true, nullable = false, columnDefinition = "TEXT")
    private String registration;

    @Column(name = "serialNumber", unique = true, columnDefinition = "TEXT")
    private String serialNumber;

    @Column(name = "owner", nullable = false, columnDefinition = "TEXT")
    private String owner;

    @Column(name = "brand", columnDefinition = "TEXT")
    private String brand;

    @Column(name = "model", columnDefinition = "TEXT")
    private String model;

    @Column(name = "motorization", columnDefinition = "TEXT")
    private String motorization;

    @Column(name = "engineCode", columnDefinition = "TEXT")
    private String engineCode;

    @Column(name = "releaseDate", columnDefinition = "DATE")
    private String releaseDate;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Exclude
    @Column(name = "certificate", columnDefinition = "BLOB")
    private byte[] certificate;

    @Exclude
    @Column(name = "picture", columnDefinition = "BLOB")
    private byte[] picture;

    @JsonManagedReference
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Intervention> interventions = new ArrayList<>(0);

    /**
     * Sets the interventions.
     *
     * @param interventions
     *     The interventions to set
     */
    public void setInterventions(final List<Intervention> interventions) {
        this.interventions.clear();

        if (nonNull(interventions)) {
            this.interventions.addAll(interventions);
        }
    }

}
