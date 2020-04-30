package fr.vulture.hostocars.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link Converter} interface for unit tests.
 */
@Slf4j
@Component
class ConverterImpl extends Converter<String, Integer> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String toEntity(final Integer dto) {
        log.trace("[DTO => Entity] With DTO = {}", dto);
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer toDto(final String entity) {
        log.trace("[DTO <= Entity] With entity = {}", entity);
        return null;
    }

}
