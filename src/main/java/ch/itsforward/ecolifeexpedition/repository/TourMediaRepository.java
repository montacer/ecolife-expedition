package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.TourMedia;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TourMedia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TourMediaRepository extends JpaRepository<TourMedia, Long> {
}
