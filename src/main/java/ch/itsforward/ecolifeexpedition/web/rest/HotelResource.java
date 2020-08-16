package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.Hotel;
import ch.itsforward.ecolifeexpedition.repository.HotelRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.Hotel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HotelResource {

    private final Logger log = LoggerFactory.getLogger(HotelResource.class);

    private static final String ENTITY_NAME = "hotel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HotelRepository hotelRepository;

    public HotelResource(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    /**
     * {@code POST  /hotels} : Create a new hotel.
     *
     * @param hotel the hotel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hotel, or with status {@code 400 (Bad Request)} if the hotel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hotels")
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) throws URISyntaxException {
        log.debug("REST request to save Hotel : {}", hotel);
        if (hotel.getId() != null) {
            throw new BadRequestAlertException("A new hotel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hotel result = hotelRepository.save(hotel);
        return ResponseEntity.created(new URI("/api/hotels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/hotels")
    public ResponseEntity<Hotel> updateHotel(@RequestBody Hotel hotel) throws URISyntaxException {
        log.debug("REST request to update Hotel : {}", hotel);
        if (hotel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Hotel result = hotelRepository.save(hotel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotel.getId().toString()))
            .body(result);
    }

    @GetMapping("/hotels")
    public List<Hotel> getAllHotels() {
        log.debug("REST request to get all Hotels");
        return hotelRepository.findAll();
    }

      @GetMapping("/hotels/{id}")
    public ResponseEntity<Hotel> getHotel(@PathVariable Long id) {
        log.debug("REST request to get Hotel : {}", id);
        Optional<Hotel> hotel = hotelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hotel);
    }

      @DeleteMapping("/hotels/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        log.debug("REST request to delete Hotel : {}", id);
        hotelRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
