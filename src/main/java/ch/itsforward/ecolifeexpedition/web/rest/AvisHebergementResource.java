package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.AvisHebergement;
import ch.itsforward.ecolifeexpedition.repository.AvisHebergementRepository;
import ch.itsforward.ecolifeexpedition.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.AvisHebergement}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AvisHebergementResource {

    private final Logger log = LoggerFactory.getLogger(AvisHebergementResource.class);

    private static final String ENTITY_NAME = "avisHebergement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvisHebergementRepository avisHebergementRepository;

    public AvisHebergementResource(AvisHebergementRepository avisHebergementRepository) {
        this.avisHebergementRepository = avisHebergementRepository;
    }

    /**
     * {@code POST  /avis-hebergements} : Create a new avisHebergement.
     *
     * @param avisHebergement the avisHebergement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avisHebergement, or with status {@code 400 (Bad Request)} if the avisHebergement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avis-hebergements")
    public ResponseEntity<AvisHebergement> createAvisHebergement(@RequestBody AvisHebergement avisHebergement) throws URISyntaxException {
        log.debug("REST request to save AvisHebergement : {}", avisHebergement);
        if (avisHebergement.getId() != null) {
            throw new BadRequestAlertException("A new avisHebergement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvisHebergement result = avisHebergementRepository.save(avisHebergement);
        return ResponseEntity.created(new URI("/api/avis-hebergements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avis-hebergements} : Updates an existing avisHebergement.
     *
     * @param avisHebergement the avisHebergement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avisHebergement,
     * or with status {@code 400 (Bad Request)} if the avisHebergement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avisHebergement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avis-hebergements")
    public ResponseEntity<AvisHebergement> updateAvisHebergement(@RequestBody AvisHebergement avisHebergement) throws URISyntaxException {
        log.debug("REST request to update AvisHebergement : {}", avisHebergement);
        if (avisHebergement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AvisHebergement result = avisHebergementRepository.save(avisHebergement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avisHebergement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /avis-hebergements} : get all the avisHebergements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avisHebergements in body.
     */
    @GetMapping("/avis-hebergements")
    public List<AvisHebergement> getAllAvisHebergements() {
        log.debug("REST request to get all AvisHebergements");
        return avisHebergementRepository.findAll();
    }

    /**
     * {@code GET  /avis-hebergements/:id} : get the "id" avisHebergement.
     *
     * @param id the id of the avisHebergement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avisHebergement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avis-hebergements/{id}")
    public ResponseEntity<AvisHebergement> getAvisHebergement(@PathVariable Long id) {
        log.debug("REST request to get AvisHebergement : {}", id);
        Optional<AvisHebergement> avisHebergement = avisHebergementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(avisHebergement);
    }

    /**
     * {@code DELETE  /avis-hebergements/:id} : delete the "id" avisHebergement.
     *
     * @param id the id of the avisHebergement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avis-hebergements/{id}")
    public ResponseEntity<Void> deleteAvisHebergement(@PathVariable Long id) {
        log.debug("REST request to delete AvisHebergement : {}", id);
        avisHebergementRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
