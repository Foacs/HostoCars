package fr.vulture.hostocars.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * DTO for the {@code cars} table.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Car extends Dto {

    private static final long serialVersionUID = -9056019196986505858L;

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

}
