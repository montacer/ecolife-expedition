package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.TarifTour;
import ch.itsforward.ecolifeexpedition.repository.TarifTourRepository;

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
 * Integration tests for the {@link TarifTourResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TarifTourResourceIT {

    private static final Float DEFAULT_MONTANT_TTC = 1F;
    private static final Float UPDATED_MONTANT_TTC = 2F;

    @Autowired
    private TarifTourRepository tarifTourRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarifTourMockMvc;

    private TarifTour tarifTour;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifTour createEntity(EntityManager em) {
        TarifTour tarifTour = new TarifTour()
            .montantTTC(DEFAULT_MONTANT_TTC);
        return tarifTour;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifTour createUpdatedEntity(EntityManager em) {
        TarifTour tarifTour = new TarifTour()
            .montantTTC(UPDATED_MONTANT_TTC);
        return tarifTour;
    }

    @BeforeEach
    public void initTest() {
        tarifTour = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarifTour() throws Exception {
        int databaseSizeBeforeCreate = tarifTourRepository.findAll().size();
        // Create the TarifTour
        restTarifTourMockMvc.perform(post("/api/tarif-tours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifTour)))
            .andExpect(status().isCreated());

        // Validate the TarifTour in the database
        List<TarifTour> tarifTourList = tarifTourRepository.findAll();
        assertThat(tarifTourList).hasSize(databaseSizeBeforeCreate + 1);
        TarifTour testTarifTour = tarifTourList.get(tarifTourList.size() - 1);
        assertThat(testTarifTour.getMontantTTC()).isEqualTo(DEFAULT_MONTANT_TTC);
    }

    @Test
    @Transactional
    public void createTarifTourWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarifTourRepository.findAll().size();

        // Create the TarifTour with an existing ID
        tarifTour.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarifTourMockMvc.perform(post("/api/tarif-tours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifTour)))
            .andExpect(status().isBadRequest());

        // Validate the TarifTour in the database
        List<TarifTour> tarifTourList = tarifTourRepository.findAll();
        assertThat(tarifTourList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTarifTours() throws Exception {
        // Initialize the database
        tarifTourRepository.saveAndFlush(tarifTour);

        // Get all the tarifTourList
        restTarifTourMockMvc.perform(get("/api/tarif-tours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifTour.getId().intValue())))
            .andExpect(jsonPath("$.[*].montantTTC").value(hasItem(DEFAULT_MONTANT_TTC.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTarifTour() throws Exception {
        // Initialize the database
        tarifTourRepository.saveAndFlush(tarifTour);

        // Get the tarifTour
        restTarifTourMockMvc.perform(get("/api/tarif-tours/{id}", tarifTour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarifTour.getId().intValue()))
            .andExpect(jsonPath("$.montantTTC").value(DEFAULT_MONTANT_TTC.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTarifTour() throws Exception {
        // Get the tarifTour
        restTarifTourMockMvc.perform(get("/api/tarif-tours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarifTour() throws Exception {
        // Initialize the database
        tarifTourRepository.saveAndFlush(tarifTour);

        int databaseSizeBeforeUpdate = tarifTourRepository.findAll().size();

        // Update the tarifTour
        TarifTour updatedTarifTour = tarifTourRepository.findById(tarifTour.getId()).get();
        // Disconnect from session so that the updates on updatedTarifTour are not directly saved in db
        em.detach(updatedTarifTour);
        updatedTarifTour
            .montantTTC(UPDATED_MONTANT_TTC);

        restTarifTourMockMvc.perform(put("/api/tarif-tours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarifTour)))
            .andExpect(status().isOk());

        // Validate the TarifTour in the database
        List<TarifTour> tarifTourList = tarifTourRepository.findAll();
        assertThat(tarifTourList).hasSize(databaseSizeBeforeUpdate);
        TarifTour testTarifTour = tarifTourList.get(tarifTourList.size() - 1);
        assertThat(testTarifTour.getMontantTTC()).isEqualTo(UPDATED_MONTANT_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingTarifTour() throws Exception {
        int databaseSizeBeforeUpdate = tarifTourRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarifTourMockMvc.perform(put("/api/tarif-tours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarifTour)))
            .andExpect(status().isBadRequest());

        // Validate the TarifTour in the database
        List<TarifTour> tarifTourList = tarifTourRepository.findAll();
        assertThat(tarifTourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarifTour() throws Exception {
        // Initialize the database
        tarifTourRepository.saveAndFlush(tarifTour);

        int databaseSizeBeforeDelete = tarifTourRepository.findAll().size();

        // Delete the tarifTour
        restTarifTourMockMvc.perform(delete("/api/tarif-tours/{id}", tarifTour.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TarifTour> tarifTourList = tarifTourRepository.findAll();
        assertThat(tarifTourList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
