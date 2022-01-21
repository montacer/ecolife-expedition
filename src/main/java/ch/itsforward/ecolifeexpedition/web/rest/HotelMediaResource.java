package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.HotelMedia;
import ch.itsforward.ecolifeexpedition.repository.HotelMediaRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.HotelMedia}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HotelMediaResource {

    private final Logger log = LoggerFactory.getLogger(HotelMediaResource.class);

    private static final String ENTITY_NAME = "hotelMedia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HotelMediaRepository hotelMediaRepository;

    public HotelMediaResource(HotelMediaRepository hotelMediaRepository) {
        this.hotelMediaRepository = hotelMediaRepository;
    }

    /**
     * {@code POST  /hotel-medias} : Create a new hotelMedia.
     *
     * @param hotelMedia the hotelMedia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hotelMedia, or with status {@code 400 (Bad Request)} if the hotelMedia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hotel-medias")
    public ResponseEntity<HotelMedia> createHotelMedia(@RequestBody HotelMedia hotelMedia) throws URISyntaxException {
        log.debug("REST request to save HotelMedia : {}", hotelMedia);
        if (hotelMedia.getId() != null) {
            throw new BadRequestAlertException("A new hotelMedia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HotelMedia result = hotelMediaRepository.save(hotelMedia);
        return ResponseEntity.created(new URI("/api/hotel-medias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hotel-medias} : Updates an existing hotelMedia.
     *
     * @param hotelMedia the hotelMedia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelMedia,
     * or with status {@code 400 (Bad Request)} if the hotelMedia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hotelMedia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hotel-medias")
    public ResponseEntity<HotelMedia> updateHotelMedia(@RequestBody HotelMedia hotelMedia) throws URISyntaxException {
        log.debug("REST request to update HotelMedia : {}", hotelMedia);
        if (hotelMedia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HotelMedia result = hotelMediaRepository.save(hotelMedia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotelMedia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hotel-medias} : get all the hotelMedias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hotelMedias in body.
     */
    @GetMapping("/hotel-medias")
    public List<HotelMedia> getAllHotelMedias() {
        log.debug("REST request to get all HotelMedias");
        return hotelMediaRepository.findAll();
    }

    /**
     * {@code GET  /hotel-medias/:id} : get the "id" hotelMedia.
     *
     * @param id the id of the hotelMedia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hotelMedia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hotel-medias/{id}")
    public ResponseEntity<HotelMedia> getHotelMedia(@PathVariable Long id) {
        log.debug("REST request to get HotelMedia : {}", id);
        Optional<HotelMedia> hotelMedia = hotelMediaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hotelMedia);
    }

    /**
     * {@code DELETE  /hotel-medias/:id} : delete the "id" hotelMedia.
     *
     * @param id the id of the hotelMedia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hotel-medias/{id}")
    public ResponseEntity<Void> deleteHotelMedia(@PathVariable Long id) {
        log.debug("REST request to delete HotelMedia : {}", id);
        hotelMediaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
