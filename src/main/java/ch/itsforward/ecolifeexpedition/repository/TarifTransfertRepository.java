package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.TarifTransfert;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TarifTransfert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifTransfertRepository extends JpaRepository<TarifTransfert, Long> {
}
