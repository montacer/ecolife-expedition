package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.AvisHebergement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AvisHebergement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvisHebergementRepository extends JpaRepository<AvisHebergement, Long> {
}
