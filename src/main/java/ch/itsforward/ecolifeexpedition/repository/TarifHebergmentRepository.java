package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.TarifHebergment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TarifHebergment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifHebergmentRepository extends JpaRepository<TarifHebergment, Long> {
}
