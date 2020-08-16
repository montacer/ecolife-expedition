package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.Tour;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Tour entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
}
