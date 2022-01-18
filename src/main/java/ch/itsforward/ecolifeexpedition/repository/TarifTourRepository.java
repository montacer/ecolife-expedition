package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.TarifTour;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TarifTour entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifTourRepository extends JpaRepository<TarifTour, Long> {
}
