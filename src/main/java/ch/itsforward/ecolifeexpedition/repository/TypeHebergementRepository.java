package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.TypeHebergement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeHebergement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeHebergementRepository extends JpaRepository<TypeHebergement, Long> {
}
