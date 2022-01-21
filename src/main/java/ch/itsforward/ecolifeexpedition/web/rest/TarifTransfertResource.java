package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.TarifTransfert;
import ch.itsforward.ecolifeexpedition.repository.TarifTransfertRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.TarifTransfert}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TarifTransfertResource {

    private final Logger log = LoggerFactory.getLogger(TarifTransfertResource.class);

    private static final String ENTITY_NAME = "tarifTransfert";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarifTransfertRepository tarifTransfertRepository;

    public TarifTransfertResource(TarifTransfertRepository tarifTransfertRepository) {
        this.tarifTransfertRepository = tarifTransfertRepository;
    }

    /**
     * {@code POST  /tarif-transferts} : Create a new tarifTransfert.
     *
     * @param tarifTransfert the tarifTransfert to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarifTransfert, or with status {@code 400 (Bad Request)} if the tarifTransfert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tarif-transferts")
    public ResponseEntity<TarifTransfert> createTarifTransfert(@RequestBody TarifTransfert tarifTransfert) throws URISyntaxException {
        log.debug("REST request to save TarifTransfert : {}", tarifTransfert);
        if (tarifTransfert.getId() != null) {
            throw new BadRequestAlertException("A new tarifTransfert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TarifTransfert result = tarifTransfertRepository.save(tarifTransfert);
        return ResponseEntity.created(new URI("/api/tarif-transferts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tarif-transferts} : Updates an existing tarifTransfert.
     *
     * @param tarifTransfert the tarifTransfert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarifTransfert,
     * or with status {@code 400 (Bad Request)} if the tarifTransfert is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarifTransfert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tarif-transferts")
    public ResponseEntity<TarifTransfert> updateTarifTransfert(@RequestBody TarifTransfert tarifTransfert) throws URISyntaxException {
        log.debug("REST request to update TarifTransfert : {}", tarifTransfert);
        if (tarifTransfert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TarifTransfert result = tarifTransfertRepository.save(tarifTransfert);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarifTransfert.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tarif-transferts} : get all the tarifTransferts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarifTransferts in body.
     */
    @GetMapping("/tarif-transferts")
    public List<TarifTransfert> getAllTarifTransferts() {
        log.debug("REST request to get all TarifTransferts");
        return tarifTransfertRepository.findAll();
    }

    /**
     * {@code GET  /tarif-transferts/:id} : get the "id" tarifTransfert.
     *
     * @param id the id of the tarifTransfert to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarifTransfert, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tarif-transferts/{id}")
    public ResponseEntity<TarifTransfert> getTarifTransfert(@PathVariable Long id) {
        log.debug("REST request to get TarifTransfert : {}", id);
        Optional<TarifTransfert> tarifTransfert = tarifTransfertRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tarifTransfert);
    }

    /**
     * {@code DELETE  /tarif-transferts/:id} : delete the "id" tarifTransfert.
     *
     * @param id the id of the tarifTransfert to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tarif-transferts/{id}")
    public ResponseEntity<Void> deleteTarifTransfert(@PathVariable Long id) {
        log.debug("REST request to delete TarifTransfert : {}", id);
        tarifTransfertRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
