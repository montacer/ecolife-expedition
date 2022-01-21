package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.ReservationTransfert;
import ch.itsforward.ecolifeexpedition.repository.ReservationTransfertRepository;

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
 * Integration tests for the {@link ReservationTransfertResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReservationTransfertResourceIT {

    private static final Float DEFAULT_MONTANT_TOTAL_TTC = 1F;
    private static final Float UPDATED_MONTANT_TOTAL_TTC = 2F;

    @Autowired
    private ReservationTransfertRepository reservationTransfertRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReservationTransfertMockMvc;

    private ReservationTransfert reservationTransfert;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservationTransfert createEntity(EntityManager em) {
        ReservationTransfert reservationTransfert = new ReservationTransfert()
            .montantTotalTTC(DEFAULT_MONTANT_TOTAL_TTC);
        return reservationTransfert;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservationTransfert createUpdatedEntity(EntityManager em) {
        ReservationTransfert reservationTransfert = new ReservationTransfert()
            .montantTotalTTC(UPDATED_MONTANT_TOTAL_TTC);
        return reservationTransfert;
    }

    @BeforeEach
    public void initTest() {
        reservationTransfert = createEntity(em);
    }

    @Test
    @Transactional
    public void createReservationTransfert() throws Exception {
        int databaseSizeBeforeCreate = reservationTransfertRepository.findAll().size();
        // Create the ReservationTransfert
        restReservationTransfertMockMvc.perform(post("/api/reservation-transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reservationTransfert)))
            .andExpect(status().isCreated());

        // Validate the ReservationTransfert in the database
        List<ReservationTransfert> reservationTransfertList = reservationTransfertRepository.findAll();
        assertThat(reservationTransfertList).hasSize(databaseSizeBeforeCreate + 1);
        ReservationTransfert testReservationTransfert = reservationTransfertList.get(reservationTransfertList.size() - 1);
        assertThat(testReservationTransfert.getMontantTotalTTC()).isEqualTo(DEFAULT_MONTANT_TOTAL_TTC);
    }

    @Test
    @Transactional
    public void createReservationTransfertWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reservationTransfertRepository.findAll().size();

        // Create the ReservationTransfert with an existing ID
        reservationTransfert.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservationTransfertMockMvc.perform(post("/api/reservation-transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reservationTransfert)))
            .andExpect(status().isBadRequest());

        // Validate the ReservationTransfert in the database
        List<ReservationTransfert> reservationTransfertList = reservationTransfertRepository.findAll();
        assertThat(reservationTransfertList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReservationTransferts() throws Exception {
        // Initialize the database
        reservationTransfertRepository.saveAndFlush(reservationTransfert);

        // Get all the reservationTransfertList
        restReservationTransfertMockMvc.perform(get("/api/reservation-transferts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservationTransfert.getId().intValue())))
            .andExpect(jsonPath("$.[*].montantTotalTTC").value(hasItem(DEFAULT_MONTANT_TOTAL_TTC.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getReservationTransfert() throws Exception {
        // Initialize the database
        reservationTransfertRepository.saveAndFlush(reservationTransfert);

        // Get the reservationTransfert
        restReservationTransfertMockMvc.perform(get("/api/reservation-transferts/{id}", reservationTransfert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reservationTransfert.getId().intValue()))
            .andExpect(jsonPath("$.montantTotalTTC").value(DEFAULT_MONTANT_TOTAL_TTC.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingReservationTransfert() throws Exception {
        // Get the reservationTransfert
        restReservationTransfertMockMvc.perform(get("/api/reservation-transferts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReservationTransfert() throws Exception {
        // Initialize the database
        reservationTransfertRepository.saveAndFlush(reservationTransfert);

        int databaseSizeBeforeUpdate = reservationTransfertRepository.findAll().size();

        // Update the reservationTransfert
        ReservationTransfert updatedReservationTransfert = reservationTransfertRepository.findById(reservationTransfert.getId()).get();
        // Disconnect from session so that the updates on updatedReservationTransfert are not directly saved in db
        em.detach(updatedReservationTransfert);
        updatedReservationTransfert
            .montantTotalTTC(UPDATED_MONTANT_TOTAL_TTC);

        restReservationTransfertMockMvc.perform(put("/api/reservation-transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedReservationTransfert)))
            .andExpect(status().isOk());

        // Validate the ReservationTransfert in the database
        List<ReservationTransfert> reservationTransfertList = reservationTransfertRepository.findAll();
        assertThat(reservationTransfertList).hasSize(databaseSizeBeforeUpdate);
        ReservationTransfert testReservationTransfert = reservationTransfertList.get(reservationTransfertList.size() - 1);
        assertThat(testReservationTransfert.getMontantTotalTTC()).isEqualTo(UPDATED_MONTANT_TOTAL_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingReservationTransfert() throws Exception {
        int databaseSizeBeforeUpdate = reservationTransfertRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservationTransfertMockMvc.perform(put("/api/reservation-transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reservationTransfert)))
            .andExpect(status().isBadRequest());

        // Validate the ReservationTransfert in the database
        List<ReservationTransfert> reservationTransfertList = reservationTransfertRepository.findAll();
        assertThat(reservationTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReservationTransfert() throws Exception {
        // Initialize the database
        reservationTransfertRepository.saveAndFlush(reservationTransfert);

        int databaseSizeBeforeDelete = reservationTransfertRepository.findAll().size();

        // Delete the reservationTransfert
        restReservationTransfertMockMvc.perform(delete("/api/reservation-transferts/{id}", reservationTransfert.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReservationTransfert> reservationTransfertList = reservationTransfertRepository.findAll();
        assertThat(reservationTransfertList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
