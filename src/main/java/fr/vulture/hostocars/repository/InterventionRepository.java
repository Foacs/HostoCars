package fr.vulture.hostocars.repository;

import fr.vulture.hostocars.entity.CarEntity;
import fr.vulture.hostocars.entity.InterventionEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the {@link InterventionEntity} entity.
 */
@Repository
public interface InterventionRepository extends JpaRepository<InterventionEntity, Integer> {

    /**
     * Returns the list of all {@link InterventionEntity} by {@link CarEntity} ID.
     *
     * @param carId
     *     The {@link CarEntity} ID
     *
     * @return a list of {@link InterventionEntity}
     */
    List<InterventionEntity> findAllByCarId(final Integer carId);

}
