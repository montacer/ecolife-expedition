package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.AvisTourMedia;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AvisTourMedia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvisTourMediaRepository extends JpaRepository<AvisTourMedia, Long> {
}
