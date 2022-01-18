package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.TypeTarif;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeTarif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeTarifRepository extends JpaRepository<TypeTarif, Long> {
}
