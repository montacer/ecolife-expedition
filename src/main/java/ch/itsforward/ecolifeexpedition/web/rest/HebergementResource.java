package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.Hebergement;
import ch.itsforward.ecolifeexpedition.repository.HebergementRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.Hebergement}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HebergementResource {

    private final Logger log = LoggerFactory.getLogger(HebergementResource.class);

    private static final String ENTITY_NAME = "hebergement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HebergementRepository hebergementRepository;

    public HebergementResource(HebergementRepository hebergementRepository) {
        this.hebergementRepository = hebergementRepository;
    }

    /**
     * {@code POST  /hebergements} : Create a new hebergement.
     *
     * @param hebergement the hebergement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hebergement, or with status {@code 400 (Bad Request)} if the hebergement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hebergements")
    public ResponseEntity<Hebergement> createHebergement(@RequestBody Hebergement hebergement) throws URISyntaxException {
        log.debug("REST request to save Hebergement : {}", hebergement);
        if (hebergement.getId() != null) {
            throw new BadRequestAlertException("A new hebergement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hebergement result = hebergementRepository.save(hebergement);
        return ResponseEntity.created(new URI("/api/hebergements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hebergements} : Updates an existing hebergement.
     *
     * @param hebergement the hebergement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hebergement,
     * or with status {@code 400 (Bad Request)} if the hebergement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hebergement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hebergements")
    public ResponseEntity<Hebergement> updateHebergement(@RequestBody Hebergement hebergement) throws URISyntaxException {
        log.debug("REST request to update Hebergement : {}", hebergement);
        if (hebergement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Hebergement result = hebergementRepository.save(hebergement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hebergement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hebergements} : get all the hebergements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hebergements in body.
     */
    @GetMapping("/hebergements")
    public List<Hebergement> getAllHebergements() {
        log.debug("REST request to get all Hebergements");
        return hebergementRepository.findAll();
    }

    /**
     * {@code GET  /hebergements/:id} : get the "id" hebergement.
     *
     * @param id the id of the hebergement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hebergement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hebergements/{id}")
    public ResponseEntity<Hebergement> getHebergement(@PathVariable Long id) {
        log.debug("REST request to get Hebergement : {}", id);
        Optional<Hebergement> hebergement = hebergementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hebergement);
    }

    /**
     * {@code DELETE  /hebergements/:id} : delete the "id" hebergement.
     *
     * @param id the id of the hebergement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hebergements/{id}")
    public ResponseEntity<Void> deleteHebergement(@PathVariable Long id) {
        log.debug("REST request to delete Hebergement : {}", id);
        hebergementRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
