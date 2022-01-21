package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.TarifHebergement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TarifHebergement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifHebergementRepository extends JpaRepository<TarifHebergement, Long> {
}
