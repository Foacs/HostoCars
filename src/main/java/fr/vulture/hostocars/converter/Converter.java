package fr.vulture.hostocars.converter;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract converter for entities and DTOs.
 *
 * @param <Entity>
 *     The entity class
 * @param <Dto>
 *     The DTO class
 */
@Slf4j
public abstract class Converter<Entity, Dto> {

    /**
     * Converts a {@link List} of {@link Dto} to a {@link List} of {@link Entity}.
     * <br/>
     * If the input {@link List} is {@code null}, returns an empty {@link List} of {@link Entity}.
     *
     * @param dtoList
     *     The {@link List} of {@link Dto} to convert
     *
     * @return a {@link List} of {@link Entity}
     */
    List<Entity> toEntityList(final List<? extends Dto> dtoList) {
        // If the input DTO list is null, returns an empty list
        if (isNull(dtoList)) {
            log.trace("[DTO list => Entity list] With null DTO list");
            return emptyList();
        }

        // Converts all DTOs to entities
        log.trace("[DTO list => Entity list] With DTO list size = {}", dtoList.size());
        return dtoList.stream().map(this::toEntity).collect(toList());
    }

    /**
     * Converts a {@link Dto} to an {@link Entity}.
     * <br/>
     * If the input {@link Dto} is {@code null}, returns a {@code null} {@link Entity}.
     *
     * @param dto
     *     The {@link Dto} to convert
     *
     * @return an {@link Entity}
     */
    abstract Entity toEntity(final Dto dto);

    /**
     * Converts a {@link List} of {@link Entity} to a {@link List} of {@link Dto}.
     * <br/>
     * If the input {@link List} is {@code null}, returns an empty {@link List} of {@link Dto}.
     *
     * @param entityList
     *     The {@link List} of {@link Entity} to convert
     *
     * @return a {@link List} of {@link Dto}
     */
    public List<Dto> toDtoList(final List<? extends Entity> entityList) {
        // If the input entity list is null, returns an empty list
        if (isNull(entityList)) {
            log.trace("[DTO list <= Entity list] With null entity list");
            return emptyList();
        }

        // Converts all entities to DTOs
        log.trace("[DTO list <= Entity list] With entity list size = {}", entityList.size());
        return entityList.stream().map(this::toDto).collect(toList());
    }

    /**
     * Converts an {@link Entity} to a {@link Dto}.
     * <br/>
     * If the input {@link Entity} is {@code null}, returns a {@code null} {@link Dto}.
     *
     * @param entity
     *     The {@link Entity} to convert
     *
     * @return a {@link Dto}
     */
    abstract Dto toDto(final Entity entity);

}
