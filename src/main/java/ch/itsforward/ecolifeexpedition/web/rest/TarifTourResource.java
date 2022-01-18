package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.TarifTour;
import ch.itsforward.ecolifeexpedition.repository.TarifTourRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.TarifTour}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TarifTourResource {

    private final Logger log = LoggerFactory.getLogger(TarifTourResource.class);

    private static final String ENTITY_NAME = "tarifTour";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarifTourRepository tarifTourRepository;

    public TarifTourResource(TarifTourRepository tarifTourRepository) {
        this.tarifTourRepository = tarifTourRepository;
    }

    /**
     * {@code POST  /tarif-tours} : Create a new tarifTour.
     *
     * @param tarifTour the tarifTour to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarifTour, or with status {@code 400 (Bad Request)} if the tarifTour has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tarif-tours")
    public ResponseEntity<TarifTour> createTarifTour(@RequestBody TarifTour tarifTour) throws URISyntaxException {
        log.debug("REST request to save TarifTour : {}", tarifTour);
        if (tarifTour.getId() != null) {
            throw new BadRequestAlertException("A new tarifTour cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TarifTour result = tarifTourRepository.save(tarifTour);
        return ResponseEntity.created(new URI("/api/tarif-tours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tarif-tours} : Updates an existing tarifTour.
     *
     * @param tarifTour the tarifTour to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarifTour,
     * or with status {@code 400 (Bad Request)} if the tarifTour is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarifTour couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tarif-tours")
    public ResponseEntity<TarifTour> updateTarifTour(@RequestBody TarifTour tarifTour) throws URISyntaxException {
        log.debug("REST request to update TarifTour : {}", tarifTour);
        if (tarifTour.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TarifTour result = tarifTourRepository.save(tarifTour);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarifTour.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tarif-tours} : get all the tarifTours.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarifTours in body.
     */
    @GetMapping("/tarif-tours")
    public List<TarifTour> getAllTarifTours() {
        log.debug("REST request to get all TarifTours");
        return tarifTourRepository.findAll();
    }

    /**
     * {@code GET  /tarif-tours/:id} : get the "id" tarifTour.
     *
     * @param id the id of the tarifTour to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarifTour, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tarif-tours/{id}")
    public ResponseEntity<TarifTour> getTarifTour(@PathVariable Long id) {
        log.debug("REST request to get TarifTour : {}", id);
        Optional<TarifTour> tarifTour = tarifTourRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tarifTour);
    }

    /**
     * {@code DELETE  /tarif-tours/:id} : delete the "id" tarifTour.
     *
     * @param id the id of the tarifTour to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tarif-tours/{id}")
    public ResponseEntity<Void> deleteTarifTour(@PathVariable Long id) {
        log.debug("REST request to delete TarifTour : {}", id);
        tarifTourRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
