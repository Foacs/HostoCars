package fr.vulture.hostocars.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString.Exclude;

/**
 * Entity for the {@code Cars} table.
 */
@Entity
@Data
@Table(name = "Cars")
public class Car implements Serializable {

    private static final long serialVersionUID = -926253460136617865L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
        unique = true,
        nullable = false,
        insertable = false,
        updatable = false,
        columnDefinition = "INTEGER")
    private Integer id;

    @Column(name = "registration",
        unique = true,
        nullable = false,
        columnDefinition = "TEXT")
    private String registration;

    @Column(name = "serialNumber",
        unique = true,
        columnDefinition = "TEXT")
    private String serialNumber;

    @Column(name = "owner",
        nullable = false,
        columnDefinition = "TEXT")
    private String owner;

    @Column(name = "brand",
        columnDefinition = "TEXT")
    private String brand;

    @Column(name = "model",
        columnDefinition = "TEXT")
    private String model;

    @Column(name = "motorization",
        columnDefinition = "TEXT")
    private String motorization;

    @Column(name = "engineCode",
        columnDefinition = "TEXT")
    private String engineCode;

    @Column(name = "releaseDate",
        columnDefinition = "DATE")
    private String releaseDate;

    @Column(name = "comments",
        columnDefinition = "TEXT")
    private String comments;

    @Column(name = "certificate",
        columnDefinition = "BLOB")
    @Exclude
    private byte[] certificate;

    @Column(name = "picture",
        columnDefinition = "BLOB")
    @Exclude
    private byte[] picture;

}
