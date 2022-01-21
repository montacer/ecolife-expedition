package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.AvisTransfert;
import ch.itsforward.ecolifeexpedition.repository.AvisTransfertRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.AvisTransfert}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AvisTransfertResource {

    private final Logger log = LoggerFactory.getLogger(AvisTransfertResource.class);

    private static final String ENTITY_NAME = "avisTransfert";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvisTransfertRepository avisTransfertRepository;

    public AvisTransfertResource(AvisTransfertRepository avisTransfertRepository) {
        this.avisTransfertRepository = avisTransfertRepository;
    }

    /**
     * {@code POST  /avis-transferts} : Create a new avisTransfert.
     *
     * @param avisTransfert the avisTransfert to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avisTransfert, or with status {@code 400 (Bad Request)} if the avisTransfert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avis-transferts")
    public ResponseEntity<AvisTransfert> createAvisTransfert(@RequestBody AvisTransfert avisTransfert) throws URISyntaxException {
        log.debug("REST request to save AvisTransfert : {}", avisTransfert);
        if (avisTransfert.getId() != null) {
            throw new BadRequestAlertException("A new avisTransfert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvisTransfert result = avisTransfertRepository.save(avisTransfert);
        return ResponseEntity.created(new URI("/api/avis-transferts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avis-transferts} : Updates an existing avisTransfert.
     *
     * @param avisTransfert the avisTransfert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avisTransfert,
     * or with status {@code 400 (Bad Request)} if the avisTransfert is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avisTransfert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avis-transferts")
    public ResponseEntity<AvisTransfert> updateAvisTransfert(@RequestBody AvisTransfert avisTransfert) throws URISyntaxException {
        log.debug("REST request to update AvisTransfert : {}", avisTransfert);
        if (avisTransfert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AvisTransfert result = avisTransfertRepository.save(avisTransfert);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avisTransfert.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /avis-transferts} : get all the avisTransferts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avisTransferts in body.
     */
    @GetMapping("/avis-transferts")
    public List<AvisTransfert> getAllAvisTransferts() {
        log.debug("REST request to get all AvisTransferts");
        return avisTransfertRepository.findAll();
    }

    /**
     * {@code GET  /avis-transferts/:id} : get the "id" avisTransfert.
     *
     * @param id the id of the avisTransfert to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avisTransfert, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avis-transferts/{id}")
    public ResponseEntity<AvisTransfert> getAvisTransfert(@PathVariable Long id) {
        log.debug("REST request to get AvisTransfert : {}", id);
        Optional<AvisTransfert> avisTransfert = avisTransfertRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(avisTransfert);
    }

    /**
     * {@code DELETE  /avis-transferts/:id} : delete the "id" avisTransfert.
     *
     * @param id the id of the avisTransfert to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avis-transferts/{id}")
    public ResponseEntity<Void> deleteAvisTransfert(@PathVariable Long id) {
        log.debug("REST request to delete AvisTransfert : {}", id);
        avisTransfertRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
