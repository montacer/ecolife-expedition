package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.TarifHebergment;
import ch.itsforward.ecolifeexpedition.repository.TarifHebergmentRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.TarifHebergment}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TarifHebergmentResource {

    private final Logger log = LoggerFactory.getLogger(TarifHebergmentResource.class);

    private static final String ENTITY_NAME = "tarifHebergment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarifHebergmentRepository tarifHebergmentRepository;

    public TarifHebergmentResource(TarifHebergmentRepository tarifHebergmentRepository) {
        this.tarifHebergmentRepository = tarifHebergmentRepository;
    }

    /**
     * {@code POST  /tarif-hebergments} : Create a new tarifHebergment.
     *
     * @param tarifHebergment the tarifHebergment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarifHebergment, or with status {@code 400 (Bad Request)} if the tarifHebergment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tarif-hebergments")
    public ResponseEntity<TarifHebergment> createTarifHebergment(@RequestBody TarifHebergment tarifHebergment) throws URISyntaxException {
        log.debug("REST request to save TarifHebergment : {}", tarifHebergment);
        if (tarifHebergment.getId() != null) {
            throw new BadRequestAlertException("A new tarifHebergment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TarifHebergment result = tarifHebergmentRepository.save(tarifHebergment);
        return ResponseEntity.created(new URI("/api/tarif-hebergments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tarif-hebergments} : Updates an existing tarifHebergment.
     *
     * @param tarifHebergment the tarifHebergment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarifHebergment,
     * or with status {@code 400 (Bad Request)} if the tarifHebergment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarifHebergment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tarif-hebergments")
    public ResponseEntity<TarifHebergment> updateTarifHebergment(@RequestBody TarifHebergment tarifHebergment) throws URISyntaxException {
        log.debug("REST request to update TarifHebergment : {}", tarifHebergment);
        if (tarifHebergment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TarifHebergment result = tarifHebergmentRepository.save(tarifHebergment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarifHebergment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tarif-hebergments} : get all the tarifHebergments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarifHebergments in body.
     */
    @GetMapping("/tarif-hebergments")
    public List<TarifHebergment> getAllTarifHebergments() {
        log.debug("REST request to get all TarifHebergments");
        return tarifHebergmentRepository.findAll();
    }

    /**
     * {@code GET  /tarif-hebergments/:id} : get the "id" tarifHebergment.
     *
     * @param id the id of the tarifHebergment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarifHebergment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tarif-hebergments/{id}")
    public ResponseEntity<TarifHebergment> getTarifHebergment(@PathVariable Long id) {
        log.debug("REST request to get TarifHebergment : {}", id);
        Optional<TarifHebergment> tarifHebergment = tarifHebergmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tarifHebergment);
    }

    /**
     * {@code DELETE  /tarif-hebergments/:id} : delete the "id" tarifHebergment.
     *
     * @param id the id of the tarifHebergment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tarif-hebergments/{id}")
    public ResponseEntity<Void> deleteTarifHebergment(@PathVariable Long id) {
        log.debug("REST request to delete TarifHebergment : {}", id);
        tarifHebergmentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
