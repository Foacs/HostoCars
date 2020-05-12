package fr.vulture.hostocars.repository;

import fr.vulture.hostocars.entity.OperationEntity;
import fr.vulture.hostocars.entity.OperationLineEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the {@link OperationLineEntity} entity.
 */
@Repository
public interface OperationLineRepository extends JpaRepository<OperationLineEntity, Integer> {

    /**
     * Returns the list of all {@link OperationLineEntity} by {@link OperationEntity} ID.
     *
     * @param operationId
     *     The {@link OperationEntity} ID
     *
     * @return a list of {@link OperationLineEntity}
     */
    List<OperationLineEntity> findAllByOperationId(final Integer operationId);

}
