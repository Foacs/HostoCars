package fr.vulture.hostocars.converter;

import static java.util.Objects.isNull;

import fr.vulture.hostocars.dto.Property;
import fr.vulture.hostocars.entity.PropertyEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link Converter} abstract class for the {@link PropertyEntity} entity and {@link Property} DTO.
 */
@Slf4j
@Component
public class PropertyConverter extends Converter<PropertyEntity, Property> {

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyEntity toEntity(final Property dto) {
        log.trace("[DTO => Entity] With DTO = {}", dto);

        // If the input DTO is null, returns null
        if (isNull(dto)) {
            return null;
        }

        // Converts the input DTO to an entity
        final PropertyEntity result = new PropertyEntity();
        result.setId(dto.getId());
        result.setKey(dto.getKey());
        result.setValue(dto.getValue());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Property toDto(final PropertyEntity entity) {
        log.trace("[DTO <= Entity] With entity = {}", entity);

        // If the input entity is null, returns null
        if (isNull(entity)) {
            return null;
        }

        // Converts the input entity to a DTO
        final Property result = new Property();
        result.setId(entity.getId());
        result.setKey(entity.getKey());
        result.setValue(entity.getValue());
        return result;
    }

}
