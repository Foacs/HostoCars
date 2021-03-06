package fr.vulture.hostocars.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import fr.vulture.hostocars.dto.Intervention;
import fr.vulture.hostocars.dto.Operation;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class for the {@link InterventionConverter} class.
 */
@DisplayName("Intervention converter")
@ExtendWith(MockitoExtension.class)
class InterventionConverterTest {

    @Mock
    private OperationConverter operationConverter;

    @InjectMocks
    private InterventionConverter interventionConverter;

    /**
     * Tests the {@link InterventionConverter#toEntity(Intervention)} method with a null DTO.
     */
    @Test
    @DisplayName("To entity (null DTO)")
    void testToEntityWithNullDto() {
        // Calls the method
        final fr.vulture.hostocars.entity.Intervention result = this.interventionConverter.toEntity(null);

        // Checks the result
        assertNull(result, "Result unexpectedly not null");
    }

    /**
     * Tests the {@link InterventionConverter#toEntity(Intervention)} method.
     */
    @Test
    @DisplayName("To entity")
    void testToEntity() {
        // Prepares the inputs
        final Integer id = 0;
        final Integer year = 1;
        final Integer number = 2;
        final String status = "status";
        final String description = "description";
        final Integer mileage = 3;
        final Double estimatedTime = 0.0d;
        final Double realTime = 1.0d;
        final Double amount = 2.0d;
        final Double paidAmount = 3.0d;
        final String comments = "comments";
        final Set<Operation> operations = mock(Set.class);
        final Intervention dto = new Intervention();
        dto.setId(id);
        dto.setYear(year);
        dto.setNumber(number);
        dto.setStatus(status);
        dto.setDescription(description);
        dto.setMileage(mileage);
        dto.setEstimatedTime(estimatedTime);
        dto.setRealTime(realTime);
        dto.setAmount(amount);
        dto.setPaidAmount(paidAmount);
        dto.setComments(comments);
        dto.setOperations(operations);

        // Prepares the intermediary results
        final Set<fr.vulture.hostocars.entity.Operation> convertedOperations = mock(Set.class);

        // Mocks the calls
        doReturn(convertedOperations).when(this.operationConverter).toEntities(operations);

        // Calls the method
        final fr.vulture.hostocars.entity.Intervention result = this.interventionConverter.toEntity(dto);

        // Checks the mocks calls
        verify(this.operationConverter).toEntities(operations);

        // Checks the result
        assertNotNull(result, "Result unexpectedly null");
        assertAll("Result different from expected", () -> assertEquals(id, result.getId(), "ID different from expected"),
            () -> assertEquals(year, result.getYear(), "Year different from expected"), () -> assertEquals(number, result.getNumber(), "Number different from expected"),
            () -> assertEquals(status, result.getStatus(), "Status different from expected"),
            () -> assertEquals(description, result.getDescription(), "Description different from expected"),
            () -> assertEquals(mileage, result.getMileage(), "Mileage different from expected"),
            () -> assertEquals(estimatedTime, result.getEstimatedTime(), "Estimated time different from expected"),
            () -> assertEquals(realTime, result.getRealTime(), "Real time different from expected"),
            () -> assertEquals(amount, result.getAmount(), "Amount different from expected"),
            () -> assertEquals(paidAmount, result.getPaidAmount(), "Paid amount different from expected"),
            () -> assertEquals(comments, result.getComments(), "Comments different from expected"),
            () -> assertEquals(convertedOperations, result.getOperations(), "Operations different from expected"));
    }

    /**
     * Tests the {@link InterventionConverter#toDto(fr.vulture.hostocars.entity.Intervention)} method with a null entity.
     */
    @Test
    @DisplayName("To DTO (null entity)")
    void testToDtoWithNullEntity() {
        // Calls the method
        final Intervention result = this.interventionConverter.toDto(null);

        // Checks the result
        assertNull(result, "Result unexpectedly not null");
    }

    /**
     * Tests the {@link InterventionConverter#toDto(fr.vulture.hostocars.entity.Intervention)} method.
     */
    @Test
    @DisplayName("To DTO")
    void testToDto() {
        // Prepares the inputs
        final Integer id = 0;
        final Integer year = 1;
        final Integer number = 2;
        final String status = "status";
        final String description = "description";
        final Integer mileage = 3;
        final Double estimatedTime = 0.0d;
        final Double realTime = 1.0d;
        final Double amount = 2.0d;
        final Double paidAmount = 3.0d;
        final String comments = "comments";
        final Set<fr.vulture.hostocars.entity.Operation> operations = mock(Set.class);
        final fr.vulture.hostocars.entity.Intervention dto = new fr.vulture.hostocars.entity.Intervention();
        dto.setId(id);
        dto.setYear(year);
        dto.setNumber(number);
        dto.setStatus(status);
        dto.setDescription(description);
        dto.setMileage(mileage);
        dto.setEstimatedTime(estimatedTime);
        dto.setRealTime(realTime);
        dto.setAmount(amount);
        dto.setPaidAmount(paidAmount);
        dto.setComments(comments);
        dto.setOperations(operations);

        // Prepares the intermediary results
        final Set<Operation> convertedOperations = mock(Set.class);

        // Mocks the calls
        doReturn(convertedOperations).when(this.operationConverter).toDtos(operations);

        // Calls the method
        final Intervention result = this.interventionConverter.toDto(dto);

        // Checks the mocks calls
        verify(this.operationConverter).toDtos(operations);

        // Checks the result
        assertNotNull(result, "Result unexpectedly null");
        assertAll("Result different from expected", () -> assertEquals(id, result.getId(), "ID different from expected"),
            () -> assertEquals(year, result.getYear(), "Year different from expected"), () -> assertEquals(number, result.getNumber(), "Number different from expected"),
            () -> assertEquals(status, result.getStatus(), "Status different from expected"),
            () -> assertEquals(description, result.getDescription(), "Description different from expected"),
            () -> assertEquals(mileage, result.getMileage(), "Mileage different from expected"),
            () -> assertEquals(estimatedTime, result.getEstimatedTime(), "Estimated time different from expected"),
            () -> assertEquals(realTime, result.getRealTime(), "Real time different from expected"),
            () -> assertEquals(amount, result.getAmount(), "Amount different from expected"),
            () -> assertEquals(paidAmount, result.getPaidAmount(), "Paid amount different from expected"),
            () -> assertEquals(comments, result.getComments(), "Comments different from expected"),
            () -> assertEquals(convertedOperations, result.getOperations(), "Operations different from expected"));
    }

}
