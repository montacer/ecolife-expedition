package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.domain.ReservationTransfert;
import ch.itsforward.ecolifeexpedition.repository.ReservationTransfertRepository;
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
 * REST controller for managing {@link ch.itsforward.ecolifeexpedition.domain.ReservationTransfert}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReservationTransfertResource {

    private final Logger log = LoggerFactory.getLogger(ReservationTransfertResource.class);

    private static final String ENTITY_NAME = "reservationTransfert";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReservationTransfertRepository reservationTransfertRepository;

    public ReservationTransfertResource(ReservationTransfertRepository reservationTransfertRepository) {
        this.reservationTransfertRepository = reservationTransfertRepository;
    }

    /**
     * {@code POST  /reservation-transferts} : Create a new reservationTransfert.
     *
     * @param reservationTransfert the reservationTransfert to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reservationTransfert, or with status {@code 400 (Bad Request)} if the reservationTransfert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reservation-transferts")
    public ResponseEntity<ReservationTransfert> createReservationTransfert(@RequestBody ReservationTransfert reservationTransfert) throws URISyntaxException {
        log.debug("REST request to save ReservationTransfert : {}", reservationTransfert);
        if (reservationTransfert.getId() != null) {
            throw new BadRequestAlertException("A new reservationTransfert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReservationTransfert result = reservationTransfertRepository.save(reservationTransfert);
        return ResponseEntity.created(new URI("/api/reservation-transferts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reservation-transferts} : Updates an existing reservationTransfert.
     *
     * @param reservationTransfert the reservationTransfert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservationTransfert,
     * or with status {@code 400 (Bad Request)} if the reservationTransfert is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reservationTransfert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reservation-transferts")
    public ResponseEntity<ReservationTransfert> updateReservationTransfert(@RequestBody ReservationTransfert reservationTransfert) throws URISyntaxException {
        log.debug("REST request to update ReservationTransfert : {}", reservationTransfert);
        if (reservationTransfert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReservationTransfert result = reservationTransfertRepository.save(reservationTransfert);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reservationTransfert.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reservation-transferts} : get all the reservationTransferts.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reservationTransferts in body.
     */
    @GetMapping("/reservation-transferts")
    public List<ReservationTransfert> getAllReservationTransferts(@RequestParam(required = false) String filter) {
        if ("reservation-is-null".equals(filter)) {
            log.debug("REST request to get all ReservationTransferts where reservation is null");
            return StreamSupport
                .stream(reservationTransfertRepository.findAll().spliterator(), false)
                .filter(reservationTransfert -> reservationTransfert.getReservation() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all ReservationTransferts");
        return reservationTransfertRepository.findAll();
    }

    /**
     * {@code GET  /reservation-transferts/:id} : get the "id" reservationTransfert.
     *
     * @param id the id of the reservationTransfert to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reservationTransfert, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reservation-transferts/{id}")
    public ResponseEntity<ReservationTransfert> getReservationTransfert(@PathVariable Long id) {
        log.debug("REST request to get ReservationTransfert : {}", id);
        Optional<ReservationTransfert> reservationTransfert = reservationTransfertRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reservationTransfert);
    }

    /**
     * {@code DELETE  /reservation-transferts/:id} : delete the "id" reservationTransfert.
     *
     * @param id the id of the reservationTransfert to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reservation-transferts/{id}")
    public ResponseEntity<Void> deleteReservationTransfert(@PathVariable Long id) {
        log.debug("REST request to delete ReservationTransfert : {}", id);
        reservationTransfertRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
