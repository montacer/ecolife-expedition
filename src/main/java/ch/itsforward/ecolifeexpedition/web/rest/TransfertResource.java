package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.Transfert;
import ch.itsforward.ecolifeexpedition.repository.TransfertRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.Transfert}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransfertResource {

    private final Logger log = LoggerFactory.getLogger(TransfertResource.class);

    private static final String ENTITY_NAME = "transfert";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransfertRepository transfertRepository;

    public TransfertResource(TransfertRepository transfertRepository) {
        this.transfertRepository = transfertRepository;
    }

    /**
     * {@code POST  /transferts} : Create a new transfert.
     *
     * @param transfert the transfert to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transfert, or with status {@code 400 (Bad Request)} if the transfert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transferts")
    public ResponseEntity<Transfert> createTransfert(@RequestBody Transfert transfert) throws URISyntaxException {
        log.debug("REST request to save Transfert : {}", transfert);
        if (transfert.getId() != null) {
            throw new BadRequestAlertException("A new transfert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Transfert result = transfertRepository.save(transfert);
        return ResponseEntity.created(new URI("/api/transferts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transferts} : Updates an existing transfert.
     *
     * @param transfert the transfert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transfert,
     * or with status {@code 400 (Bad Request)} if the transfert is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transfert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transferts")
    public ResponseEntity<Transfert> updateTransfert(@RequestBody Transfert transfert) throws URISyntaxException {
        log.debug("REST request to update Transfert : {}", transfert);
        if (transfert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Transfert result = transfertRepository.save(transfert);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transfert.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transferts} : get all the transferts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transferts in body.
     */
    @GetMapping("/transferts")
    public List<Transfert> getAllTransferts() {
        log.debug("REST request to get all Transferts");
        return transfertRepository.findAll();
    }

    /**
     * {@code GET  /transferts/:id} : get the "id" transfert.
     *
     * @param id the id of the transfert to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transfert, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transferts/{id}")
    public ResponseEntity<Transfert> getTransfert(@PathVariable Long id) {
        log.debug("REST request to get Transfert : {}", id);
        Optional<Transfert> transfert = transfertRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transfert);
    }

    /**
     * {@code DELETE  /transferts/:id} : delete the "id" transfert.
     *
     * @param id the id of the transfert to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transferts/{id}")
    public ResponseEntity<Void> deleteTransfert(@PathVariable Long id) {
        log.debug("REST request to delete Transfert : {}", id);
        transfertRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
