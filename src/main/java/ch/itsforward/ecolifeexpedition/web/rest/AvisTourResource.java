package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.AvisTour;
import ch.itsforward.ecolifeexpedition.repository.AvisTourRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.AvisTour}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AvisTourResource {

    private final Logger log = LoggerFactory.getLogger(AvisTourResource.class);

    private static final String ENTITY_NAME = "avisTour";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvisTourRepository avisTourRepository;

    public AvisTourResource(AvisTourRepository avisTourRepository) {
        this.avisTourRepository = avisTourRepository;
    }

    /**
     * {@code POST  /avis-tours} : Create a new avisTour.
     *
     * @param avisTour the avisTour to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avisTour, or with status {@code 400 (Bad Request)} if the avisTour has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avis-tours")
    public ResponseEntity<AvisTour> createAvisTour(@RequestBody AvisTour avisTour) throws URISyntaxException {
        log.debug("REST request to save AvisTour : {}", avisTour);
        if (avisTour.getId() != null) {
            throw new BadRequestAlertException("A new avisTour cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvisTour result = avisTourRepository.save(avisTour);
        return ResponseEntity.created(new URI("/api/avis-tours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avis-tours} : Updates an existing avisTour.
     *
     * @param avisTour the avisTour to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avisTour,
     * or with status {@code 400 (Bad Request)} if the avisTour is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avisTour couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avis-tours")
    public ResponseEntity<AvisTour> updateAvisTour(@RequestBody AvisTour avisTour) throws URISyntaxException {
        log.debug("REST request to update AvisTour : {}", avisTour);
        if (avisTour.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AvisTour result = avisTourRepository.save(avisTour);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avisTour.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /avis-tours} : get all the avisTours.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avisTours in body.
     */
    @GetMapping("/avis-tours")
    public List<AvisTour> getAllAvisTours() {
        log.debug("REST request to get all AvisTours");
        return avisTourRepository.findAll();
    }

    /**
     * {@code GET  /avis-tours/:id} : get the "id" avisTour.
     *
     * @param id the id of the avisTour to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avisTour, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avis-tours/{id}")
    public ResponseEntity<AvisTour> getAvisTour(@PathVariable Long id) {
        log.debug("REST request to get AvisTour : {}", id);
        Optional<AvisTour> avisTour = avisTourRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(avisTour);
    }

    /**
     * {@code DELETE  /avis-tours/:id} : delete the "id" avisTour.
     *
     * @param id the id of the avisTour to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avis-tours/{id}")
    public ResponseEntity<Void> deleteAvisTour(@PathVariable Long id) {
        log.debug("REST request to delete AvisTour : {}", id);
        avisTourRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
