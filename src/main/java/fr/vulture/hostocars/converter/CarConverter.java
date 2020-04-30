package fr.vulture.hostocars.converter;

import static java.util.Objects.isNull;

import fr.vulture.hostocars.dto.Car;
import fr.vulture.hostocars.entity.CarEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link Converter} interface for the {@link CarEntity} entity and {@link Car} DTO.
 */
@Slf4j
@Component
public class CarConverter extends Converter<CarEntity, Car> {

    /**
     * {@inheritDoc}
     */
    @Override
    public CarEntity toEntity(final Car dto) {
        log.trace("[DTO => Entity] With DTO = {}", dto);

        // If the input DTO is null, returns null
        if (isNull(dto)) {
            return null;
        }

        // Converts the input DTO to an entity
        final CarEntity result = new CarEntity();
        result.setId(dto.getId());
        result.setRegistration(dto.getRegistration());
        result.setSerialNumber(dto.getSerialNumber());
        result.setOwner(dto.getOwner());
        result.setBrand(dto.getBrand());
        result.setModel(dto.getModel());
        result.setMotorization(dto.getMotorization());
        result.setEngineCode(dto.getEngineCode());
        result.setReleaseDate(dto.getReleaseDate());
        result.setComments(dto.getComments());
        result.setCertificate(dto.getCertificate());
        result.setPicture(dto.getPicture());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Car toDto(final CarEntity entity) {
        log.trace("[DTO <= Entity] With entity = {}", entity);

        // If the input entity is null, returns null
        if (isNull(entity)) {
            return null;
        }

        // Converts the input entity to a DTO
        final Car result = new Car();
        result.setId(entity.getId());
        result.setRegistration(entity.getRegistration());
        result.setSerialNumber(entity.getSerialNumber());
        result.setOwner(entity.getOwner());
        result.setBrand(entity.getBrand());
        result.setModel(entity.getModel());
        result.setMotorization(entity.getMotorization());
        result.setEngineCode(entity.getEngineCode());
        result.setReleaseDate(entity.getReleaseDate());
        result.setComments(entity.getComments());
        result.setCertificate(entity.getCertificate());
        result.setPicture(entity.getPicture());
        return result;
    }

}
