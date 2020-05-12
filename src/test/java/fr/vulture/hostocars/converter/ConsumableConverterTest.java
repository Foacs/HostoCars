package fr.vulture.hostocars.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import fr.vulture.hostocars.dto.Consumable;
import fr.vulture.hostocars.entity.ConsumableEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link ConsumableConverter} class.
 */
@DisplayName("Consumable converter")
class ConsumableConverterTest {

    private final ConsumableConverter converter = new ConsumableConverter();

    /**
     * Tests the {@link ConsumableConverter#toEntity} method.
     */
    @Test
    @DisplayName("Converting to entity")
    void testConvertToEntity() {
        final Consumable dto = new Consumable();
        final Integer id = 0;
        dto.setId(id);
        final Integer interventionId = 1;
        dto.setInterventionId(interventionId);
        final String denomination = "denomination";
        dto.setDenomination(denomination);
        final String quantity = "quantity";
        dto.setQuantity(quantity);

        final ConsumableEntity entity = this.converter.toEntity(dto);

        assertNotNull(entity, "Entity unexpectedly not null");
        assertEquals(id, entity.getId(), "ID different from expected");
        assertEquals(interventionId, entity.getInterventionId(), "Intervention ID different from expected");
        assertEquals(denomination, entity.getDenomination(), "Denomination different from expected");
        assertEquals(quantity, entity.getQuantity(), "Quantity different from expected");
    }

    /**
     * Tests the {@link ConsumableConverter#toEntity} method with a null DTO.
     */
    @Test
    @DisplayName("Converting to entity (null DTO)")
    void testConvertNullDtoToEntity() {
        assertNull(this.converter.toEntity(null), "Entity unexpectedly not null");
    }

    /**
     * Tests the {@link ConsumableConverter#toDto} method.
     */
    @Test
    @DisplayName("Converting to DTO")
    void testConvertToDto() {
        final ConsumableEntity entity = new ConsumableEntity();
        final Integer id = 0;
        entity.setId(id);
        final Integer interventionId = 1;
        entity.setInterventionId(interventionId);
        final String denomination = "denomination";
        entity.setDenomination(denomination);
        final String quantity = "quantity";
        entity.setQuantity(quantity);

        final Consumable dto = this.converter.toDto(entity);

        assertNotNull(dto, "DTO unexpectedly not null");
        assertEquals(id, dto.getId(), "ID different from expected");
        assertEquals(interventionId, dto.getInterventionId(), "Intervention ID different from expected");
        assertEquals(denomination, dto.getDenomination(), "Denomination different from expected");
        assertEquals(quantity, dto.getQuantity(), "Quantity different from expected");
    }

    /**
     * Tests the {@link ConsumableConverter#toDto} method with a null entity.
     */
    @Test
    @DisplayName("Converting to DTO (null entity)")
    void testConvertNullEntityToDto() {
        assertNull(this.converter.toDto(null), "DTO unexpectedly not null");
    }

}
