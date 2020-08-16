package ch.itsforward.ecolifeexpedition.repository;

import ch.itsforward.ecolifeexpedition.domain.ServiceSupplementaire;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceSupplementaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceSupplementaireRepository extends JpaRepository<ServiceSupplementaire, Long> {
}
