package fr.vulture.hostocars.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import fr.vulture.hostocars.dto.Intervention;
import fr.vulture.hostocars.entity.InterventionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link InterventionConverter} class.
 */
@DisplayName("Intervention converter")
class InterventionConverterTest {

    private final InterventionConverter converter = new InterventionConverter();

    /**
     * Tests the {@link InterventionConverter#toEntity} method.
     */
    @Test
    @DisplayName("Converting to entity")
    void testConvertToEntity() {
        final Intervention dto = new Intervention();
        final Integer id = 0;
        dto.setId(id);
        final Integer carId = 1;
        dto.setCarId(carId);
        final Integer creationYear = 2;
        dto.setCreationYear(creationYear);
        final Integer number = 3;
        dto.setNumber(number);
        final String status = "status";
        dto.setStatus(status);
        final String description = "description";
        dto.setDescription(description);
        final Integer mileage = 4;
        dto.setMileage(mileage);
        final Double estimatedTime = 5.0;
        dto.setEstimatedTime(estimatedTime);
        final Double realTime = 6.0;
        dto.setRealTime(realTime);
        final Double amount = 7.0;
        dto.setAmount(amount);
        final Double paidAmount = 8.0;
        dto.setPaidAmount(paidAmount);

        final InterventionEntity entity = this.converter.toEntity(dto);

        assertNotNull(entity, "Entity unexpectedly not null");
        assertEquals(id, entity.getId(), "ID different from expected");
        assertEquals(carId, entity.getCarId(), "Car ID different from expected");
        assertEquals(creationYear, entity.getCreationYear(), "Creation year different from expected");
        assertEquals(number, entity.getNumber(), "Number different from expected");
        assertEquals(status, entity.getStatus(), "Status different from expected");
        assertEquals(description, entity.getDescription(), "Description different from expected");
        assertEquals(mileage, entity.getMileage(), "Mileage different from expected");
        assertEquals(estimatedTime, entity.getEstimatedTime(), "Estimated time different from expected");
        assertEquals(realTime, entity.getRealTime(), "Real time different from expected");
        assertEquals(amount, entity.getAmount(), "Amount different from expected");
        assertEquals(paidAmount, entity.getPaidAmount(), "Paid amount different from expected");
    }

    /**
     * Tests the {@link InterventionConverter#toEntity} method with a null DTO.
     */
    @Test
    @DisplayName("Converting to entity (null DTO)")
    void testConvertNullDtoToEntity() {
        assertNull(this.converter.toEntity(null), "Entity unexpectedly not null");
    }

    /**
     * Tests the {@link InterventionConverter#toDto} method.
     */
    @Test
    @DisplayName("Converting to DTO")
    void testConvertToDto() {
        final InterventionEntity entity = new InterventionEntity();
        final Integer id = 0;
        entity.setId(id);
        final Integer carId = 1;
        entity.setCarId(carId);
        final Integer creationYear = 2;
        entity.setCreationYear(creationYear);
        final Integer number = 3;
        entity.setNumber(number);
        final String status = "status";
        entity.setStatus(status);
        final String description = "description";
        entity.setDescription(description);
        final Integer mileage = 4;
        entity.setMileage(mileage);
        final Double estimatedTime = 5.0;
        entity.setEstimatedTime(estimatedTime);
        final Double realTime = 6.0;
        entity.setRealTime(realTime);
        final Double amount = 7.0;
        entity.setAmount(amount);
        final Double paidAmount = 8.0;
        entity.setPaidAmount(paidAmount);

        final Intervention dto = this.converter.toDto(entity);

        assertNotNull(dto, "DTO unexpectedly not null");
        assertEquals(id, dto.getId(), "ID different from expected");
        assertEquals(carId, dto.getCarId(), "Car ID different from expected");
        assertEquals(creationYear, dto.getCreationYear(), "Creation year different from expected");
        assertEquals(number, dto.getNumber(), "Number different from expected");
        assertEquals(status, dto.getStatus(), "Status different from expected");
        assertEquals(description, dto.getDescription(), "Description different from expected");
        assertEquals(mileage, dto.getMileage(), "Mileage different from expected");
        assertEquals(estimatedTime, dto.getEstimatedTime(), "Estimated time different from expected");
        assertEquals(realTime, dto.getRealTime(), "Real time different from expected");
        assertEquals(amount, dto.getAmount(), "Amount different from expected");
        assertEquals(paidAmount, dto.getPaidAmount(), "Paid amount different from expected");
    }

    /**
     * Tests the {@link InterventionConverter#toDto} method with a null entity.
     */
    @Test
    @DisplayName("Converting to DTO (null entity)")
    void testConvertNullEntityToDto() {
        assertNull(this.converter.toDto(null), "DTO unexpectedly not null");
    }

}
