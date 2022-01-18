package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.TarifServiceSupplementaire;
import ch.itsforward.ecolifeexpedition.repository.TarifServiceSupplementaireRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.TarifServiceSupplementaire}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TarifServiceSupplementaireResource {

    private final Logger log = LoggerFactory.getLogger(TarifServiceSupplementaireResource.class);

    private static final String ENTITY_NAME = "tarifServiceSupplementaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarifServiceSupplementaireRepository tarifServiceSupplementaireRepository;

    public TarifServiceSupplementaireResource(TarifServiceSupplementaireRepository tarifServiceSupplementaireRepository) {
        this.tarifServiceSupplementaireRepository = tarifServiceSupplementaireRepository;
    }

    /**
     * {@code POST  /tarif-service-supplementaires} : Create a new tarifServiceSupplementaire.
     *
     * @param tarifServiceSupplementaire the tarifServiceSupplementaire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarifServiceSupplementaire, or with status {@code 400 (Bad Request)} if the tarifServiceSupplementaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tarif-service-supplementaires")
    public ResponseEntity<TarifServiceSupplementaire> createTarifServiceSupplementaire(@RequestBody TarifServiceSupplementaire tarifServiceSupplementaire) throws URISyntaxException {
        log.debug("REST request to save TarifServiceSupplementaire : {}", tarifServiceSupplementaire);
        if (tarifServiceSupplementaire.getId() != null) {
            throw new BadRequestAlertException("A new tarifServiceSupplementaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TarifServiceSupplementaire result = tarifServiceSupplementaireRepository.save(tarifServiceSupplementaire);
        return ResponseEntity.created(new URI("/api/tarif-service-supplementaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tarif-service-supplementaires} : Updates an existing tarifServiceSupplementaire.
     *
     * @param tarifServiceSupplementaire the tarifServiceSupplementaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarifServiceSupplementaire,
     * or with status {@code 400 (Bad Request)} if the tarifServiceSupplementaire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarifServiceSupplementaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tarif-service-supplementaires")
    public ResponseEntity<TarifServiceSupplementaire> updateTarifServiceSupplementaire(@RequestBody TarifServiceSupplementaire tarifServiceSupplementaire) throws URISyntaxException {
        log.debug("REST request to update TarifServiceSupplementaire : {}", tarifServiceSupplementaire);
        if (tarifServiceSupplementaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TarifServiceSupplementaire result = tarifServiceSupplementaireRepository.save(tarifServiceSupplementaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarifServiceSupplementaire.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tarif-service-supplementaires} : get all the tarifServiceSupplementaires.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarifServiceSupplementaires in body.
     */
    @GetMapping("/tarif-service-supplementaires")
    public List<TarifServiceSupplementaire> getAllTarifServiceSupplementaires() {
        log.debug("REST request to get all TarifServiceSupplementaires");
        return tarifServiceSupplementaireRepository.findAll();
    }

    /**
     * {@code GET  /tarif-service-supplementaires/:id} : get the "id" tarifServiceSupplementaire.
     *
     * @param id the id of the tarifServiceSupplementaire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarifServiceSupplementaire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tarif-service-supplementaires/{id}")
    public ResponseEntity<TarifServiceSupplementaire> getTarifServiceSupplementaire(@PathVariable Long id) {
        log.debug("REST request to get TarifServiceSupplementaire : {}", id);
        Optional<TarifServiceSupplementaire> tarifServiceSupplementaire = tarifServiceSupplementaireRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tarifServiceSupplementaire);
    }

    /**
     * {@code DELETE  /tarif-service-supplementaires/:id} : delete the "id" tarifServiceSupplementaire.
     *
     * @param id the id of the tarifServiceSupplementaire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tarif-service-supplementaires/{id}")
    public ResponseEntity<Void> deleteTarifServiceSupplementaire(@PathVariable Long id) {
        log.debug("REST request to delete TarifServiceSupplementaire : {}", id);
        tarifServiceSupplementaireRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
