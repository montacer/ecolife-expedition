package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.ReservationTour;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReservationTour entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservationTourRepository extends JpaRepository<ReservationTour, Long> {
}
