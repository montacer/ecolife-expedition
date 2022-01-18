package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.TarifServiceSupplementaire;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TarifServiceSupplementaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifServiceSupplementaireRepository extends JpaRepository<TarifServiceSupplementaire, Long> {
}
