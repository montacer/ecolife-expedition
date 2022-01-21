package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.TypeTransfert;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeTransfert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeTransfertRepository extends JpaRepository<TypeTransfert, Long> {
}
