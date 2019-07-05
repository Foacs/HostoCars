package fr.vulture.hostocars.model;

import static java.util.Objects.nonNull;

import java.time.LocalDate;
import lombok.Data;
import org.slf4j.helpers.MessageFormatter;

/**
 * Car entity.
 */
@Data
public class Car {

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

    @Override
    public String toString() {
        Object[] arguments = new Object[]{
            id,
            owner,
            registration,
            brand,
            model,
            motorization,
            releaseDate,
            nonNull(certificate),
            nonNull(comments),
            nonNull(picture)
        };

        return MessageFormatter.arrayFormat(
            "Contact { ID: {} | Owner: {} | Registration: {} | Brand: {} | Model: {} | Motorization: {} | Release date: {} | Certificate: {} | Comments: {} | Picture: {} }",
            arguments).getMessage();
    }

}
