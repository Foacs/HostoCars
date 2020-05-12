package fr.vulture.hostocars.converter;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import fr.vulture.hostocars.dto.Car;
import fr.vulture.hostocars.entity.CarEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link CarConverter} class.
 */
@DisplayName("Car converter")
class CarConverterTest {

    private final CarConverter converter = new CarConverter();

    /**
     * Tests the {@link CarConverter#toEntity} method.
     */
    @Test
    @DisplayName("Converting to entity")
    void testConvertToEntity() {
        final Car dto = new Car();
        final Integer id = 0;
        dto.setId(id);
        final String registration = "registration";
        dto.setRegistration(registration);
        final String serialNumber = "serialNumber";
        dto.setSerialNumber(serialNumber);
        final String owner = "owner";
        dto.setOwner(owner);
        final String brand = "brand";
        dto.setBrand(brand);
        final String model = "model";
        dto.setModel(model);
        final String motorization = "motorization";
        dto.setMotorization(motorization);
        final String engineCode = "engineCode";
        dto.setEngineCode(engineCode);
        final String releaseDate = "releaseDate";
        dto.setReleaseDate(releaseDate);
        final String comments = "comments";
        dto.setComments(comments);
        final byte[] certificate = new byte[] {(byte) 0};
        dto.setCertificate(certificate);
        final byte[] picture = new byte[] {(byte) 1};
        dto.setPicture(picture);

        final CarEntity entity = this.converter.toEntity(dto);

        assertNotNull(entity, "Entity unexpectedly not null");
        assertEquals(id, entity.getId(), "ID different from expected");
        assertEquals(registration, entity.getRegistration(), "Registration different from expected");
        assertEquals(serialNumber, entity.getSerialNumber(), "Serial number different from expected");
        assertEquals(owner, entity.getOwner(), "Owner different from expected");
        assertEquals(brand, entity.getBrand(), "Brand different from expected");
        assertEquals(model, entity.getModel(), "Model different from expected");
        assertEquals(motorization, entity.getMotorization(), "Motorization different from expected");
        assertEquals(engineCode, entity.getEngineCode(), "Engine code different from expected");
        assertEquals(releaseDate, entity.getReleaseDate(), "Release date different from expected");
        assertEquals(comments, entity.getComments(), "Comments different from expected");
        assertArrayEquals(certificate, entity.getCertificate(), "Certificate different from expected");
        assertArrayEquals(picture, entity.getPicture(), "Picture different from expected");
    }

    /**
     * Tests the {@link CarConverter#toEntity} method with a null DTO.
     */
    @Test
    @DisplayName("Converting to entity (null DTO)")
    void testConvertNullDtoToEntity() {
        assertNull(this.converter.toEntity(null), "Entity unexpectedly not null");
    }

    /**
     * Tests the {@link CarConverter#toDto} method.
     */
    @Test
    @DisplayName("Converting to DTO")
    void testConvertToDto() {
        final CarEntity entity = new CarEntity();
        final Integer id = 0;
        entity.setId(id);
        final String registration = "registration";
        entity.setRegistration(registration);
        final String serialNumber = "serialNumber";
        entity.setSerialNumber(serialNumber);
        final String owner = "owner";
        entity.setOwner(owner);
        final String brand = "brand";
        entity.setBrand(brand);
        final String model = "model";
        entity.setModel(model);
        final String motorization = "motorization";
        entity.setMotorization(motorization);
        final String engineCode = "engineCode";
        entity.setEngineCode(engineCode);
        final String releaseDate = "releaseDate";
        entity.setReleaseDate(releaseDate);
        final String comments = "comments";
        entity.setComments(comments);
        final byte[] certificate = new byte[] {(byte) 0};
        entity.setCertificate(certificate);
        final byte[] picture = new byte[] {(byte) 1};
        entity.setPicture(picture);

        final Car dto = this.converter.toDto(entity);

        assertNotNull(dto, "DTO unexpectedly not null");
        assertEquals(id, dto.getId(), "ID different from expected");
        assertEquals(registration, dto.getRegistration(), "Registration different from expected");
        assertEquals(serialNumber, dto.getSerialNumber(), "Serial number different from expected");
        assertEquals(owner, dto.getOwner(), "Owner different from expected");
        assertEquals(brand, dto.getBrand(), "Brand different from expected");
        assertEquals(model, dto.getModel(), "Model different from expected");
        assertEquals(motorization, dto.getMotorization(), "Motorization different from expected");
        assertEquals(engineCode, dto.getEngineCode(), "Engine code different from expected");
        assertEquals(releaseDate, dto.getReleaseDate(), "Release date different from expected");
        assertEquals(comments, dto.getComments(), "Comments different from expected");
        assertArrayEquals(certificate, dto.getCertificate(), "Certificate different from expected");
        assertArrayEquals(picture, dto.getPicture(), "Picture different from expected");
    }

    /**
     * Tests the {@link CarConverter#toDto} method with a null entity.
     */
    @Test
    @DisplayName("Converting to DTO (null entity)")
    void testConvertNullEntityToDto() {
        assertNull(this.converter.toDto(null), "DTO unexpectedly not null");
    }

}
