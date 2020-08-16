package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.Agence;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Agence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgenceRepository extends JpaRepository<Agence, Long> {
}
