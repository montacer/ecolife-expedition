package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.AvisTour;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AvisTour entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvisTourRepository extends JpaRepository<AvisTour, Long> {
}
