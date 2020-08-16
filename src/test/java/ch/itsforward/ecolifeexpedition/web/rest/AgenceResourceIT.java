package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.Agence;
import ch.itsforward.ecolifeexpedition.repository.AgenceRepository;

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
 * Integration tests for the {@link AgenceResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AgenceResourceIT {

    private static final String DEFAULT_LIB_AGENCE = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AGENCE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_AGENCE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_AGENCE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_AGENCE = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_AGENCE = "BBBBBBBBBB";

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgenceMockMvc;

    private Agence agence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agence createEntity(EntityManager em) {
        Agence agence = new Agence()
            .libAgence(DEFAULT_LIB_AGENCE)
            .adresseAgence(DEFAULT_ADRESSE_AGENCE)
            .contactAgence(DEFAULT_CONTACT_AGENCE);
        return agence;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agence createUpdatedEntity(EntityManager em) {
        Agence agence = new Agence()
            .libAgence(UPDATED_LIB_AGENCE)
            .adresseAgence(UPDATED_ADRESSE_AGENCE)
            .contactAgence(UPDATED_CONTACT_AGENCE);
        return agence;
    }

    @BeforeEach
    public void initTest() {
        agence = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgence() throws Exception {
        int databaseSizeBeforeCreate = agenceRepository.findAll().size();
        // Create the Agence
        restAgenceMockMvc.perform(post("/api/agences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agence)))
            .andExpect(status().isCreated());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeCreate + 1);
        Agence testAgence = agenceList.get(agenceList.size() - 1);
        assertThat(testAgence.getLibAgence()).isEqualTo(DEFAULT_LIB_AGENCE);
        assertThat(testAgence.getAdresseAgence()).isEqualTo(DEFAULT_ADRESSE_AGENCE);
        assertThat(testAgence.getContactAgence()).isEqualTo(DEFAULT_CONTACT_AGENCE);
    }

    @Test
    @Transactional
    public void createAgenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agenceRepository.findAll().size();

        // Create the Agence with an existing ID
        agence.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgenceMockMvc.perform(post("/api/agences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agence)))
            .andExpect(status().isBadRequest());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAgences() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList
        restAgenceMockMvc.perform(get("/api/agences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agence.getId().intValue())))
            .andExpect(jsonPath("$.[*].libAgence").value(hasItem(DEFAULT_LIB_AGENCE)))
            .andExpect(jsonPath("$.[*].adresseAgence").value(hasItem(DEFAULT_ADRESSE_AGENCE)))
            .andExpect(jsonPath("$.[*].contactAgence").value(hasItem(DEFAULT_CONTACT_AGENCE)));
    }
    
    @Test
    @Transactional
    public void getAgence() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get the agence
        restAgenceMockMvc.perform(get("/api/agences/{id}", agence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agence.getId().intValue()))
            .andExpect(jsonPath("$.libAgence").value(DEFAULT_LIB_AGENCE))
            .andExpect(jsonPath("$.adresseAgence").value(DEFAULT_ADRESSE_AGENCE))
            .andExpect(jsonPath("$.contactAgence").value(DEFAULT_CONTACT_AGENCE));
    }
    @Test
    @Transactional
    public void getNonExistingAgence() throws Exception {
        // Get the agence
        restAgenceMockMvc.perform(get("/api/agences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgence() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        int databaseSizeBeforeUpdate = agenceRepository.findAll().size();

        // Update the agence
        Agence updatedAgence = agenceRepository.findById(agence.getId()).get();
        // Disconnect from session so that the updates on updatedAgence are not directly saved in db
        em.detach(updatedAgence);
        updatedAgence
            .libAgence(UPDATED_LIB_AGENCE)
            .adresseAgence(UPDATED_ADRESSE_AGENCE)
            .contactAgence(UPDATED_CONTACT_AGENCE);

        restAgenceMockMvc.perform(put("/api/agences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgence)))
            .andExpect(status().isOk());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
        Agence testAgence = agenceList.get(agenceList.size() - 1);
        assertThat(testAgence.getLibAgence()).isEqualTo(UPDATED_LIB_AGENCE);
        assertThat(testAgence.getAdresseAgence()).isEqualTo(UPDATED_ADRESSE_AGENCE);
        assertThat(testAgence.getContactAgence()).isEqualTo(UPDATED_CONTACT_AGENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingAgence() throws Exception {
        int databaseSizeBeforeUpdate = agenceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgenceMockMvc.perform(put("/api/agences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agence)))
            .andExpect(status().isBadRequest());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgence() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        int databaseSizeBeforeDelete = agenceRepository.findAll().size();

        // Delete the agence
        restAgenceMockMvc.perform(delete("/api/agences/{id}", agence.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
