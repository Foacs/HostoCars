package fr.vulture.hostocars.repository;

import fr.vulture.hostocars.entity.PropertyEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the {@link PropertyEntity} entity.
 */
@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Integer> {

    /**
     * Returns the {@link PropertyEntity} corresponding to the given key.
     *
     * @param key
     *     The key
     *
     * @return the {@link PropertyEntity} corresponding to the given key
     */
    Optional<PropertyEntity> findByKey(final String key);

}
