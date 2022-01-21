package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.TarifHebergement;
import ch.itsforward.ecolifeexpedition.repository.TarifHebergementRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.TarifHebergement}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TarifHebergementResource {

    private final Logger log = LoggerFactory.getLogger(TarifHebergementResource.class);

    private static final String ENTITY_NAME = "tarifHebergement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarifHebergementRepository tarifHebergementRepository;

    public TarifHebergementResource(TarifHebergementRepository tarifHebergementRepository) {
        this.tarifHebergementRepository = tarifHebergementRepository;
    }

    /**
     * {@code POST  /tarif-hebergements} : Create a new tarifHebergement.
     *
     * @param tarifHebergement the tarifHebergement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarifHebergement, or with status {@code 400 (Bad Request)} if the tarifHebergement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tarif-hebergements")
    public ResponseEntity<TarifHebergement> createTarifHebergement(@RequestBody TarifHebergement tarifHebergement) throws URISyntaxException {
        log.debug("REST request to save TarifHebergement : {}", tarifHebergement);
        if (tarifHebergement.getId() != null) {
            throw new BadRequestAlertException("A new tarifHebergement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TarifHebergement result = tarifHebergementRepository.save(tarifHebergement);
        return ResponseEntity.created(new URI("/api/tarif-hebergements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tarif-hebergements} : Updates an existing tarifHebergement.
     *
     * @param tarifHebergement the tarifHebergement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarifHebergement,
     * or with status {@code 400 (Bad Request)} if the tarifHebergement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarifHebergement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tarif-hebergements")
    public ResponseEntity<TarifHebergement> updateTarifHebergement(@RequestBody TarifHebergement tarifHebergement) throws URISyntaxException {
        log.debug("REST request to update TarifHebergement : {}", tarifHebergement);
        if (tarifHebergement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TarifHebergement result = tarifHebergementRepository.save(tarifHebergement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarifHebergement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tarif-hebergements} : get all the tarifHebergements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarifHebergements in body.
     */
    @GetMapping("/tarif-hebergements")
    public List<TarifHebergement> getAllTarifHebergements() {
        log.debug("REST request to get all TarifHebergements");
        return tarifHebergementRepository.findAll();
    }

    /**
     * {@code GET  /tarif-hebergements/:id} : get the "id" tarifHebergement.
     *
     * @param id the id of the tarifHebergement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarifHebergement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tarif-hebergements/{id}")
    public ResponseEntity<TarifHebergement> getTarifHebergement(@PathVariable Long id) {
        log.debug("REST request to get TarifHebergement : {}", id);
        Optional<TarifHebergement> tarifHebergement = tarifHebergementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tarifHebergement);
    }

    /**
     * {@code DELETE  /tarif-hebergements/:id} : delete the "id" tarifHebergement.
     *
     * @param id the id of the tarifHebergement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tarif-hebergements/{id}")
    public ResponseEntity<Void> deleteTarifHebergement(@PathVariable Long id) {
        log.debug("REST request to delete TarifHebergement : {}", id);
        tarifHebergementRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
