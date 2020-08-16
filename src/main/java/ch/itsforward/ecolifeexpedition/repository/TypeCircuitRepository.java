package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.TypeCircuit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeCircuit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeCircuitRepository extends JpaRepository<TypeCircuit, Long> {
}
