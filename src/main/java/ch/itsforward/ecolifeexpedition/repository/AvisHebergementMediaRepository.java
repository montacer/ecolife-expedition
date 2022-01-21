package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.AvisHebergementMedia;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AvisHebergementMedia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvisHebergementMediaRepository extends JpaRepository<AvisHebergementMedia, Long> {
}
