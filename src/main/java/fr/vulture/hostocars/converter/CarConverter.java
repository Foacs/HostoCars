package fr.vulture.hostocars.converter;

import static java.util.Objects.isNull;

import fr.vulture.hostocars.dto.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link Converter} interface for cars.
 */
@Component
public class CarConverter implements Converter<fr.vulture.hostocars.entity.Car, Car> {

    private final InterventionConverter interventionConverter;

    /**
     * Valued autowired constructor.
     *
     * @param interventionConverter
     *     The autowired {@link InterventionConverter} component
     */
    @Autowired
    public CarConverter(final InterventionConverter interventionConverter) {
        this.interventionConverter = interventionConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public fr.vulture.hostocars.entity.Car toEntity(final Car dto) {
        if (isNull(dto)) {
            return null;
        }

        final fr.vulture.hostocars.entity.Car entity = new fr.vulture.hostocars.entity.Car();
        entity.setId(dto.getId());
        entity.setRegistration(dto.getRegistration());
        entity.setSerialNumber(dto.getSerialNumber());
        entity.setOwner(dto.getOwner());
        entity.setBrand(dto.getBrand());
        entity.setModel(dto.getModel());
        entity.setMotorization(dto.getMotorization());
        entity.setEngineCode(dto.getEngineCode());
        entity.setReleaseDate(dto.getReleaseDate());
        entity.setComments(dto.getComments());
        entity.setCertificate(dto.getCertificate());
        entity.setPicture(dto.getPicture());
        entity.setInterventions(this.interventionConverter.toEntities(dto.getInterventions()));
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Car toDto(final fr.vulture.hostocars.entity.Car entity) {
        if (isNull(entity)) {
            return null;
        }

        final Car dto = new Car();
        dto.setId(entity.getId());
        dto.setRegistration(entity.getRegistration());
        dto.setSerialNumber(entity.getSerialNumber());
        dto.setOwner(entity.getOwner());
        dto.setBrand(entity.getBrand());
        dto.setModel(entity.getModel());
        dto.setMotorization(entity.getMotorization());
        dto.setEngineCode(entity.getEngineCode());
        dto.setReleaseDate(entity.getReleaseDate());
        dto.setComments(entity.getComments());
        dto.setCertificate(entity.getCertificate());
        dto.setPicture(entity.getPicture());
        dto.setInterventions(this.interventionConverter.toDtos(entity.getInterventions()));
        return dto;
    }

}
