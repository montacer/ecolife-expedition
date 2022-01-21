package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.ReservationTour;
import ch.itsforward.ecolifeexpedition.repository.ReservationTourRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.ReservationTour}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReservationTourResource {

    private final Logger log = LoggerFactory.getLogger(ReservationTourResource.class);

    private static final String ENTITY_NAME = "reservationTour";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReservationTourRepository reservationTourRepository;

    public ReservationTourResource(ReservationTourRepository reservationTourRepository) {
        this.reservationTourRepository = reservationTourRepository;
    }

    /**
     * {@code POST  /reservation-tours} : Create a new reservationTour.
     *
     * @param reservationTour the reservationTour to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reservationTour, or with status {@code 400 (Bad Request)} if the reservationTour has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reservation-tours")
    public ResponseEntity<ReservationTour> createReservationTour(@RequestBody ReservationTour reservationTour) throws URISyntaxException {
        log.debug("REST request to save ReservationTour : {}", reservationTour);
        if (reservationTour.getId() != null) {
            throw new BadRequestAlertException("A new reservationTour cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReservationTour result = reservationTourRepository.save(reservationTour);
        return ResponseEntity.created(new URI("/api/reservation-tours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reservation-tours} : Updates an existing reservationTour.
     *
     * @param reservationTour the reservationTour to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservationTour,
     * or with status {@code 400 (Bad Request)} if the reservationTour is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reservationTour couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reservation-tours")
    public ResponseEntity<ReservationTour> updateReservationTour(@RequestBody ReservationTour reservationTour) throws URISyntaxException {
        log.debug("REST request to update ReservationTour : {}", reservationTour);
        if (reservationTour.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReservationTour result = reservationTourRepository.save(reservationTour);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reservationTour.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reservation-tours} : get all the reservationTours.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reservationTours in body.
     */
    @GetMapping("/reservation-tours")
    public List<ReservationTour> getAllReservationTours(@RequestParam(required = false) String filter) {
        if ("reservation-is-null".equals(filter)) {
            log.debug("REST request to get all ReservationTours where reservation is null");
            return StreamSupport
                .stream(reservationTourRepository.findAll().spliterator(), false)
                .filter(reservationTour -> reservationTour.getReservation() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all ReservationTours");
        return reservationTourRepository.findAll();
    }

    /**
     * {@code GET  /reservation-tours/:id} : get the "id" reservationTour.
     *
     * @param id the id of the reservationTour to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reservationTour, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reservation-tours/{id}")
    public ResponseEntity<ReservationTour> getReservationTour(@PathVariable Long id) {
        log.debug("REST request to get ReservationTour : {}", id);
        Optional<ReservationTour> reservationTour = reservationTourRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reservationTour);
    }

    /**
     * {@code DELETE  /reservation-tours/:id} : delete the "id" reservationTour.
     *
     * @param id the id of the reservationTour to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reservation-tours/{id}")
    public ResponseEntity<Void> deleteReservationTour(@PathVariable Long id) {
        log.debug("REST request to delete ReservationTour : {}", id);
        reservationTourRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
