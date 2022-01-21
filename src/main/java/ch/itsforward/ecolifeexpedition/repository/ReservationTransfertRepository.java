package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.ReservationTransfert;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReservationTransfert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservationTransfertRepository extends JpaRepository<ReservationTransfert, Long> {
}
