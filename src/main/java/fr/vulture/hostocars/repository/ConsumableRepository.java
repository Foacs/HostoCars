package fr.vulture.hostocars.repository;

import fr.vulture.hostocars.entity.ConsumableEntity;
import fr.vulture.hostocars.entity.InterventionEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the {@link ConsumableEntity} entity.
 */
@Repository
public interface ConsumableRepository extends JpaRepository<ConsumableEntity, Integer> {

    /**
     * Returns the list of all {@link ConsumableEntity} by {@link InterventionEntity} ID.
     *
     * @param interventionId
     *     The {@link InterventionEntity} ID
     *
     * @return a list of {@link ConsumableEntity}
     */
    List<ConsumableEntity> findAllByInterventionId(final Integer interventionId);

}
