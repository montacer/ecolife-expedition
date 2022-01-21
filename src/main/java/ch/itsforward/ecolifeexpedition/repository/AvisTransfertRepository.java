package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.AvisTransfert;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AvisTransfert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvisTransfertRepository extends JpaRepository<AvisTransfert, Long> {
}
