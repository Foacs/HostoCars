package fr.vulture.hostocars.repository;

import fr.vulture.hostocars.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the {@link Property} entity.
 */
@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    /**
     * Returns the {@link Property} corresponding to the given key.
     *
     * @param key
     *     The key
     *
     * @return the {@link Property} corresponding to the given key
     */
    Property findByKey(final String key);

}
