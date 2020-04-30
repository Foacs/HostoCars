package fr.vulture.hostocars.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * DTO for the {@code Cars} table.
 */
@Data
public class Car implements Serializable {

    private static final long serialVersionUID = -385838658676806162L;

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
    private byte[] certificate;
    private byte[] picture;

}
