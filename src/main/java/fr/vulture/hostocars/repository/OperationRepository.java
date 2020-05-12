package fr.vulture.hostocars.repository;

import fr.vulture.hostocars.entity.InterventionEntity;
import fr.vulture.hostocars.entity.OperationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the {@link OperationEntity} entity.
 */
@Repository
public interface OperationRepository extends JpaRepository<OperationEntity, Integer> {

    /**
     * Returns the list of all {@link OperationEntity} by {@link InterventionEntity} ID.
     *
     * @param interventionId
     *     The {@link InterventionEntity} ID
     *
     * @return a list of {@link OperationEntity}
     */
    List<OperationEntity> findAllByInterventionId(final Integer interventionId);

}
