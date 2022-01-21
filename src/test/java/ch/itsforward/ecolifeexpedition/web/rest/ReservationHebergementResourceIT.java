package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.ReservationHebergement;
import ch.itsforward.ecolifeexpedition.repository.ReservationHebergementRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReservationHebergementResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReservationHebergementResourceIT {

    private static final Float DEFAULT_MONTANT_TOTAL_TTC = 1F;
    private static final Float UPDATED_MONTANT_TOTAL_TTC = 2F;

    @Autowired
    private ReservationHebergementRepository reservationHebergementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReservationHebergementMockMvc;

    private ReservationHebergement reservationHebergement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservationHebergement createEntity(EntityManager em) {
        ReservationHebergement reservationHebergement = new ReservationHebergement()
            .montantTotalTTC(DEFAULT_MONTANT_TOTAL_TTC);
        return reservationHebergement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservationHebergement createUpdatedEntity(EntityManager em) {
        ReservationHebergement reservationHebergement = new ReservationHebergement()
            .montantTotalTTC(UPDATED_MONTANT_TOTAL_TTC);
        return reservationHebergement;
    }

    @BeforeEach
    public void initTest() {
        reservationHebergement = createEntity(em);
    }

    @Test
    @Transactional
    public void createReservationHebergement() throws Exception {
        int databaseSizeBeforeCreate = reservationHebergementRepository.findAll().size();
        // Create the ReservationHebergement
        restReservationHebergementMockMvc.perform(post("/api/reservation-hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reservationHebergement)))
            .andExpect(status().isCreated());

        // Validate the ReservationHebergement in the database
        List<ReservationHebergement> reservationHebergementList = reservationHebergementRepository.findAll();
        assertThat(reservationHebergementList).hasSize(databaseSizeBeforeCreate + 1);
        ReservationHebergement testReservationHebergement = reservationHebergementList.get(reservationHebergementList.size() - 1);
        assertThat(testReservationHebergement.getMontantTotalTTC()).isEqualTo(DEFAULT_MONTANT_TOTAL_TTC);
    }

    @Test
    @Transactional
    public void createReservationHebergementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reservationHebergementRepository.findAll().size();

        // Create the ReservationHebergement with an existing ID
        reservationHebergement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservationHebergementMockMvc.perform(post("/api/reservation-hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reservationHebergement)))
            .andExpect(status().isBadRequest());

        // Validate the ReservationHebergement in the database
        List<ReservationHebergement> reservationHebergementList = reservationHebergementRepository.findAll();
        assertThat(reservationHebergementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReservationHebergements() throws Exception {
        // Initialize the database
        reservationHebergementRepository.saveAndFlush(reservationHebergement);

        // Get all the reservationHebergementList
        restReservationHebergementMockMvc.perform(get("/api/reservation-hebergements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservationHebergement.getId().intValue())))
            .andExpect(jsonPath("$.[*].montantTotalTTC").value(hasItem(DEFAULT_MONTANT_TOTAL_TTC.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getReservationHebergement() throws Exception {
        // Initialize the database
        reservationHebergementRepository.saveAndFlush(reservationHebergement);

        // Get the reservationHebergement
        restReservationHebergementMockMvc.perform(get("/api/reservation-hebergements/{id}", reservationHebergement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reservationHebergement.getId().intValue()))
            .andExpect(jsonPath("$.montantTotalTTC").value(DEFAULT_MONTANT_TOTAL_TTC.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingReservationHebergement() throws Exception {
        // Get the reservationHebergement
        restReservationHebergementMockMvc.perform(get("/api/reservation-hebergements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReservationHebergement() throws Exception {
        // Initialize the database
        reservationHebergementRepository.saveAndFlush(reservationHebergement);

        int databaseSizeBeforeUpdate = reservationHebergementRepository.findAll().size();

        // Update the reservationHebergement
        ReservationHebergement updatedReservationHebergement = reservationHebergementRepository.findById(reservationHebergement.getId()).get();
        // Disconnect from session so that the updates on updatedReservationHebergement are not directly saved in db
        em.detach(updatedReservationHebergement);
        updatedReservationHebergement
            .montantTotalTTC(UPDATED_MONTANT_TOTAL_TTC);

        restReservationHebergementMockMvc.perform(put("/api/reservation-hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedReservationHebergement)))
            .andExpect(status().isOk());

        // Validate the ReservationHebergement in the database
        List<ReservationHebergement> reservationHebergementList = reservationHebergementRepository.findAll();
        assertThat(reservationHebergementList).hasSize(databaseSizeBeforeUpdate);
        ReservationHebergement testReservationHebergement = reservationHebergementList.get(reservationHebergementList.size() - 1);
        assertThat(testReservationHebergement.getMontantTotalTTC()).isEqualTo(UPDATED_MONTANT_TOTAL_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingReservationHebergement() throws Exception {
        int databaseSizeBeforeUpdate = reservationHebergementRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservationHebergementMockMvc.perform(put("/api/reservation-hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reservationHebergement)))
            .andExpect(status().isBadRequest());

        // Validate the ReservationHebergement in the database
        List<ReservationHebergement> reservationHebergementList = reservationHebergementRepository.findAll();
        assertThat(reservationHebergementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReservationHebergement() throws Exception {
        // Initialize the database
        reservationHebergementRepository.saveAndFlush(reservationHebergement);

        int databaseSizeBeforeDelete = reservationHebergementRepository.findAll().size();

        // Delete the reservationHebergement
        restReservationHebergementMockMvc.perform(delete("/api/reservation-hebergements/{id}", reservationHebergement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReservationHebergement> reservationHebergementList = reservationHebergementRepository.findAll();
        assertThat(reservationHebergementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
