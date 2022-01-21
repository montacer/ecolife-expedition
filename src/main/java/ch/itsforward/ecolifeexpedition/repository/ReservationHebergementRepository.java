package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.ReservationHebergement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReservationHebergement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservationHebergementRepository extends JpaRepository<ReservationHebergement, Long> {
}
