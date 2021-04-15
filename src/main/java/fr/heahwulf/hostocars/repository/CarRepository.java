package fr.heahwulf.hostocars.repository;

import fr.heahwulf.hostocars.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the {@link Car} entity.
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

}
