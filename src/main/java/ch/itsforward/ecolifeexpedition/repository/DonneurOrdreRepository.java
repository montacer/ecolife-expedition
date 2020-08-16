package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.DonneurOrdre;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DonneurOrdre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonneurOrdreRepository extends JpaRepository<DonneurOrdre, Long> {
}
