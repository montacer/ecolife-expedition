package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.Hotel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Hotel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
