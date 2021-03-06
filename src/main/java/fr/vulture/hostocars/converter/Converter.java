package fr.vulture.hostocars.converter;

import static java.util.Objects.isNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Converter interface for converting entities to DTOs and vice-versa.
 *
 * @param <E>
 *     The entity type
 * @param <D>
 *     The DTO type
 */
interface Converter<E, D> {

    /**
     * Converts a {@link Set} of DTOs to a {@link Set} of entities.
     *
     * @param dtos
     *     The {@link Set} of DTOs to convert
     *
     * @return the converted {@link Set} of entities
     */
    default Set<E> toEntities(final Collection<D> dtos) {
        return isNull(dtos) ? new HashSet<>(0) : dtos.stream().map(this::toEntity).collect(Collectors.toSet());
    }

    /**
     * Converts the given DTO to an entity.
     *
     * @param dto
     *     The DTO to convert
     *
     * @return the converted entity
     */
    E toEntity(final D dto);

    /**
     * Converts a {@link Set} of entities to a {@link Set} of DTOs.
     *
     * @param entities
     *     The {@link Set} of entities to convert
     *
     * @return the converted {@link Set} of DTOs
     */
    default Set<D> toDtos(final Collection<E> entities) {
        return isNull(entities) ? new HashSet<>(0) : entities.stream().map(this::toDto).collect(Collectors.toSet());
    }

    /**
     * Converts the given entity to a DTO.
     *
     * @param entity
     *     The entity to convert
     *
     * @return the converted DTO
     */
    D toDto(final E entity);

}
