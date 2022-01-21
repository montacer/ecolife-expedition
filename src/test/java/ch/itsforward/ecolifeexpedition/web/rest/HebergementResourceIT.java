package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.Hebergement;
import ch.itsforward.ecolifeexpedition.repository.HebergementRepository;

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
 * Integration tests for the {@link HebergementResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class HebergementResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final Float DEFAULT_LATTITUDE = 1F;
    private static final Float UPDATED_LATTITUDE = 2F;

    private static final Float DEFAULT_LONGITUDE = 1F;
    private static final Float UPDATED_LONGITUDE = 2F;

    private static final Float DEFAULT_MONTANT_T_TC = 1F;
    private static final Float UPDATED_MONTANT_T_TC = 2F;

    @Autowired
    private HebergementRepository hebergementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHebergementMockMvc;

    private Hebergement hebergement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hebergement createEntity(EntityManager em) {
        Hebergement hebergement = new Hebergement()
            .description(DEFAULT_DESCRIPTION)
            .adresse(DEFAULT_ADRESSE)
            .lattitude(DEFAULT_LATTITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .montantTTc(DEFAULT_MONTANT_T_TC);
        return hebergement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hebergement createUpdatedEntity(EntityManager em) {
        Hebergement hebergement = new Hebergement()
            .description(UPDATED_DESCRIPTION)
            .adresse(UPDATED_ADRESSE)
            .lattitude(UPDATED_LATTITUDE)
            .longitude(UPDATED_LONGITUDE)
            .montantTTc(UPDATED_MONTANT_T_TC);
        return hebergement;
    }

    @BeforeEach
    public void initTest() {
        hebergement = createEntity(em);
    }

    @Test
    @Transactional
    public void createHebergement() throws Exception {
        int databaseSizeBeforeCreate = hebergementRepository.findAll().size();
        // Create the Hebergement
        restHebergementMockMvc.perform(post("/api/hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hebergement)))
            .andExpect(status().isCreated());

        // Validate the Hebergement in the database
        List<Hebergement> hebergementList = hebergementRepository.findAll();
        assertThat(hebergementList).hasSize(databaseSizeBeforeCreate + 1);
        Hebergement testHebergement = hebergementList.get(hebergementList.size() - 1);
        assertThat(testHebergement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHebergement.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testHebergement.getLattitude()).isEqualTo(DEFAULT_LATTITUDE);
        assertThat(testHebergement.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testHebergement.getMontantTTc()).isEqualTo(DEFAULT_MONTANT_T_TC);
    }

    @Test
    @Transactional
    public void createHebergementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hebergementRepository.findAll().size();

        // Create the Hebergement with an existing ID
        hebergement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHebergementMockMvc.perform(post("/api/hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hebergement)))
            .andExpect(status().isBadRequest());

        // Validate the Hebergement in the database
        List<Hebergement> hebergementList = hebergementRepository.findAll();
        assertThat(hebergementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHebergements() throws Exception {
        // Initialize the database
        hebergementRepository.saveAndFlush(hebergement);

        // Get all the hebergementList
        restHebergementMockMvc.perform(get("/api/hebergements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hebergement.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].lattitude").value(hasItem(DEFAULT_LATTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].montantTTc").value(hasItem(DEFAULT_MONTANT_T_TC.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getHebergement() throws Exception {
        // Initialize the database
        hebergementRepository.saveAndFlush(hebergement);

        // Get the hebergement
        restHebergementMockMvc.perform(get("/api/hebergements/{id}", hebergement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hebergement.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.lattitude").value(DEFAULT_LATTITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.montantTTc").value(DEFAULT_MONTANT_T_TC.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingHebergement() throws Exception {
        // Get the hebergement
        restHebergementMockMvc.perform(get("/api/hebergements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHebergement() throws Exception {
        // Initialize the database
        hebergementRepository.saveAndFlush(hebergement);

        int databaseSizeBeforeUpdate = hebergementRepository.findAll().size();

        // Update the hebergement
        Hebergement updatedHebergement = hebergementRepository.findById(hebergement.getId()).get();
        // Disconnect from session so that the updates on updatedHebergement are not directly saved in db
        em.detach(updatedHebergement);
        updatedHebergement
            .description(UPDATED_DESCRIPTION)
            .adresse(UPDATED_ADRESSE)
            .lattitude(UPDATED_LATTITUDE)
            .longitude(UPDATED_LONGITUDE)
            .montantTTc(UPDATED_MONTANT_T_TC);

        restHebergementMockMvc.perform(put("/api/hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHebergement)))
            .andExpect(status().isOk());

        // Validate the Hebergement in the database
        List<Hebergement> hebergementList = hebergementRepository.findAll();
        assertThat(hebergementList).hasSize(databaseSizeBeforeUpdate);
        Hebergement testHebergement = hebergementList.get(hebergementList.size() - 1);
        assertThat(testHebergement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHebergement.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testHebergement.getLattitude()).isEqualTo(UPDATED_LATTITUDE);
        assertThat(testHebergement.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testHebergement.getMontantTTc()).isEqualTo(UPDATED_MONTANT_T_TC);
    }

    @Test
    @Transactional
    public void updateNonExistingHebergement() throws Exception {
        int databaseSizeBeforeUpdate = hebergementRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHebergementMockMvc.perform(put("/api/hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hebergement)))
            .andExpect(status().isBadRequest());

        // Validate the Hebergement in the database
        List<Hebergement> hebergementList = hebergementRepository.findAll();
        assertThat(hebergementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHebergement() throws Exception {
        // Initialize the database
        hebergementRepository.saveAndFlush(hebergement);

        int databaseSizeBeforeDelete = hebergementRepository.findAll().size();

        // Delete the hebergement
        restHebergementMockMvc.perform(delete("/api/hebergements/{id}", hebergement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hebergement> hebergementList = hebergementRepository.findAll();
        assertThat(hebergementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
