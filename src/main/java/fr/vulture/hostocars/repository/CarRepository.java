package fr.vulture.hostocars.repository;

import fr.vulture.hostocars.entity.CarEntity;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for the {@link CarEntity} entity.
 */
@Repository
public interface CarRepository extends JpaRepository<CarEntity, Integer> {

    /**
     * Returns the set of all distinct car registration numbers from the database.
     *
     * @return a set of distinct car registration numbers
     */
    @Query("SELECT DISTINCT car.registration FROM CarEntity car")
    Set<String> findDistinctRegistrationNumbers();

}
