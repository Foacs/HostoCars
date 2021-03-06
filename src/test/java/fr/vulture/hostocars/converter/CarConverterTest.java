package fr.vulture.hostocars.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import fr.vulture.hostocars.dto.Car;
import fr.vulture.hostocars.dto.Intervention;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class for the {@link CarConverter} class.
 */
@DisplayName("Car converter")
@ExtendWith(MockitoExtension.class)
class CarConverterTest {

    @Mock
    private InterventionConverter interventionConverter;

    @InjectMocks
    private CarConverter carConverter;

    /**
     * Tests the {@link CarConverter#toEntity(Car)} method with a null DTO.
     */
    @Test
    @DisplayName("To entity (null DTO)")
    void testToEntityWithNullDto() {
        // Calls the method
        final fr.vulture.hostocars.entity.Car result = this.carConverter.toEntity(null);

        // Checks the result
        assertNull(result, "Result unexpectedly not null");
    }

    /**
     * Tests the {@link CarConverter#toEntity(Car)} method.
     */
    @Test
    @DisplayName("To entity")
    void testToEntity() {
        // Prepares the inputs
        final Integer id = 0;
        final String registration = "registration";
        final String serialNumber = "serialNumber";
        final String owner = "owner";
        final String brand = "brand";
        final String model = "model";
        final String motorization = "motorization";
        final String engineCode = "engineCode";
        final String releaseDate = "releaseDate";
        final String comments = "comments";
        final byte[] certificate = new byte[] {(byte) 0};
        final byte[] picture = new byte[] {(byte) 1};
        final Set<Intervention> interventions = mock(Set.class);
        final Car dto = new Car();
        dto.setId(id);
        dto.setRegistration(registration);
        dto.setSerialNumber(serialNumber);
        dto.setOwner(owner);
        dto.setBrand(brand);
        dto.setModel(model);
        dto.setMotorization(motorization);
        dto.setEngineCode(engineCode);
        dto.setReleaseDate(releaseDate);
        dto.setComments(comments);
        dto.setCertificate(certificate);
        dto.setPicture(picture);
        dto.setInterventions(interventions);

        // Prepares the intermediary results
        final Set<fr.vulture.hostocars.entity.Intervention> convertedInterventions = mock(Set.class);

        // Mocks the calls
        doReturn(convertedInterventions).when(this.interventionConverter).toEntities(interventions);

        // Calls the method
        final fr.vulture.hostocars.entity.Car result = this.carConverter.toEntity(dto);

        // Checks the mocks calls
        verify(this.interventionConverter).toEntities(interventions);

        // Checks the result
        assertNotNull(result, "Result unexpectedly null");
        assertAll("Result different from expected", () -> assertEquals(id, result.getId(), "ID different from expected"),
            () -> assertEquals(registration, result.getRegistration(), "Registration different from expected"),
            () -> assertEquals(serialNumber, result.getSerialNumber(), "Serial number different from expected"),
            () -> assertEquals(owner, result.getOwner(), "Owner different from expected"), () -> assertEquals(brand, result.getBrand(), "Brand different from expected"),
            () -> assertEquals(model, result.getModel(), "Model different from expected"),
            () -> assertEquals(motorization, result.getMotorization(), "Motorization different from expected"),
            () -> assertEquals(engineCode, result.getEngineCode(), "Engine code different from expected"),
            () -> assertEquals(releaseDate, result.getReleaseDate(), "Release date different from expected"),
            () -> assertEquals(comments, result.getComments(), "Comments different from expected"),
            () -> assertArrayEquals(certificate, result.getCertificate(), "Certificate different from expected"),
            () -> assertArrayEquals(picture, result.getPicture(), "Picture different from expected"),
            () -> assertEquals(convertedInterventions, result.getInterventions(), "Interventions different from expected"));
    }

    /**
     * Tests the {@link CarConverter#toDto(fr.vulture.hostocars.entity.Car)} method with a null entity.
     */
    @Test
    @DisplayName("To DTO (null entity)")
    void testToDtoWithNullEntity() {
        // Calls the method
        final Car result = this.carConverter.toDto(null);

        // Checks the result
        assertNull(result, "Result unexpectedly not null");
    }

    /**
     * Tests the {@link CarConverter#toDto(fr.vulture.hostocars.entity.Car)} method.
     */
    @Test
    @DisplayName("To DTO")
    void testToDto() {
        // Prepares the inputs
        final Integer id = 0;
        final String registration = "registration";
        final String serialNumber = "serialNumber";
        final String owner = "owner";
        final String brand = "brand";
        final String model = "model";
        final String motorization = "motorization";
        final String engineCode = "engineCode";
        final String releaseDate = "releaseDate";
        final String comments = "comments";
        final byte[] certificate = new byte[] {(byte) 0};
        final byte[] picture = new byte[] {(byte) 1};
        final Set<fr.vulture.hostocars.entity.Intervention> interventions = mock(Set.class);
        final fr.vulture.hostocars.entity.Car dto = new fr.vulture.hostocars.entity.Car();
        dto.setId(id);
        dto.setRegistration(registration);
        dto.setSerialNumber(serialNumber);
        dto.setOwner(owner);
        dto.setBrand(brand);
        dto.setModel(model);
        dto.setMotorization(motorization);
        dto.setEngineCode(engineCode);
        dto.setReleaseDate(releaseDate);
        dto.setComments(comments);
        dto.setCertificate(certificate);
        dto.setPicture(picture);
        dto.setInterventions(interventions);

        // Prepares the intermediary results
        final Set<Intervention> convertedInterventions = mock(Set.class);

        // Mocks the calls
        doReturn(convertedInterventions).when(this.interventionConverter).toDtos(interventions);

        // Calls the method
        final Car result = this.carConverter.toDto(dto);

        // Checks the mocks calls
        verify(this.interventionConverter).toDtos(interventions);

        // Checks the result
        assertNotNull(result, "Result unexpectedly null");
        assertAll("Result different from expected", () -> assertEquals(id, result.getId(), "ID different from expected"),
            () -> assertEquals(registration, result.getRegistration(), "Registration different from expected"),
            () -> assertEquals(serialNumber, result.getSerialNumber(), "Serial number different from expected"),
            () -> assertEquals(owner, result.getOwner(), "Owner different from expected"), () -> assertEquals(brand, result.getBrand(), "Brand different from expected"),
            () -> assertEquals(model, result.getModel(), "Model different from expected"),
            () -> assertEquals(motorization, result.getMotorization(), "Motorization different from expected"),
            () -> assertEquals(engineCode, result.getEngineCode(), "Engine code different from expected"),
            () -> assertEquals(releaseDate, result.getReleaseDate(), "Release date different from expected"),
            () -> assertEquals(comments, result.getComments(), "Comments different from expected"),
            () -> assertArrayEquals(certificate, result.getCertificate(), "Certificate different from expected"),
            () -> assertArrayEquals(picture, result.getPicture(), "Picture different from expected"),
            () -> assertEquals(convertedInterventions, result.getInterventions(), "Interventions different from expected"));
    }

}
