package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.HotelMedia;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the HotelMedia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HotelMediaRepository extends JpaRepository<HotelMedia, Long> {
}
