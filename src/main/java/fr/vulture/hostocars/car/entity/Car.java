package fr.vulture.hostocars.car.entity;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Entity corresponding to the {@code Cars} table.
 */
@Data
public class Car {

    @NotNull
    private Integer id;
    private String owner;
    private String registration;
    private String brand;
    private String model;
    private String motorization;
    private LocalDate releaseDate;
    private byte[] certificate;
    private String comments;
    private byte[] picture;

}
