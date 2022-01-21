package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.TypeRegion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeRegion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeRegionRepository extends JpaRepository<TypeRegion, Long> {
}
