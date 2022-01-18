package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.TourMedia;
import ch.itsforward.ecolifeexpedition.repository.TourMediaRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.TourMedia}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TourMediaResource {

    private final Logger log = LoggerFactory.getLogger(TourMediaResource.class);

    private static final String ENTITY_NAME = "tourMedia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TourMediaRepository tourMediaRepository;

    public TourMediaResource(TourMediaRepository tourMediaRepository) {
        this.tourMediaRepository = tourMediaRepository;
    }

    /**
     * {@code POST  /tour-medias} : Create a new tourMedia.
     *
     * @param tourMedia the tourMedia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tourMedia, or with status {@code 400 (Bad Request)} if the tourMedia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tour-medias")
    public ResponseEntity<TourMedia> createTourMedia(@RequestBody TourMedia tourMedia) throws URISyntaxException {
        log.debug("REST request to save TourMedia : {}", tourMedia);
        if (tourMedia.getId() != null) {
            throw new BadRequestAlertException("A new tourMedia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TourMedia result = tourMediaRepository.save(tourMedia);
        return ResponseEntity.created(new URI("/api/tour-medias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tour-medias} : Updates an existing tourMedia.
     *
     * @param tourMedia the tourMedia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tourMedia,
     * or with status {@code 400 (Bad Request)} if the tourMedia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tourMedia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tour-medias")
    public ResponseEntity<TourMedia> updateTourMedia(@RequestBody TourMedia tourMedia) throws URISyntaxException {
        log.debug("REST request to update TourMedia : {}", tourMedia);
        if (tourMedia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TourMedia result = tourMediaRepository.save(tourMedia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tourMedia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tour-medias} : get all the tourMedias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tourMedias in body.
     */
    @GetMapping("/tour-medias")
    public List<TourMedia> getAllTourMedias() {
        log.debug("REST request to get all TourMedias");
        return tourMediaRepository.findAll();
    }

    /**
     * {@code GET  /tour-medias/:id} : get the "id" tourMedia.
     *
     * @param id the id of the tourMedia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tourMedia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tour-medias/{id}")
    public ResponseEntity<TourMedia> getTourMedia(@PathVariable Long id) {
        log.debug("REST request to get TourMedia : {}", id);
        Optional<TourMedia> tourMedia = tourMediaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tourMedia);
    }

    /**
     * {@code DELETE  /tour-medias/:id} : delete the "id" tourMedia.
     *
     * @param id the id of the tourMedia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tour-medias/{id}")
    public ResponseEntity<Void> deleteTourMedia(@PathVariable Long id) {
        log.debug("REST request to delete TourMedia : {}", id);
        tourMediaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
