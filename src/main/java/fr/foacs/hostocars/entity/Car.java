package fr.foacs.hostocars.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.foacs.hostocars.configuration.Hide;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity for the {@code cars} table.
 */
@Entity
@Getter
@Setter
@Table(name = "cars")
public class Car extends AbstractEntity {

    private static final long serialVersionUID = -8531072274006990095L;

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

    @Hide(asBoolean = true)
    @Column(name = "certificate", columnDefinition = "BLOB")
    private byte[] certificate;

    @Hide(asBoolean = true)
    @Column(name = "picture", columnDefinition = "BLOB")
    private byte[] picture;

    @JsonManagedReference
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Intervention> interventions = new HashSet<>(0);

}

