package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.AvisHebergement;
import ch.itsforward.ecolifeexpedition.repository.AvisHebergementRepository;

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

import ch.itsforward.ecolifeexpedition.domain.enumeration.Stars;
/**
 * Integration tests for the {@link AvisHebergementResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AvisHebergementResourceIT {

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final Stars DEFAULT_NBRE_ETOILE = Stars.ONE;
    private static final Stars UPDATED_NBRE_ETOILE = Stars.TWO;

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    @Autowired
    private AvisHebergementRepository avisHebergementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvisHebergementMockMvc;

    private AvisHebergement avisHebergement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvisHebergement createEntity(EntityManager em) {
        AvisHebergement avisHebergement = new AvisHebergement()
            .author(DEFAULT_AUTHOR)
            .nbreEtoile(DEFAULT_NBRE_ETOILE)
            .commentaire(DEFAULT_COMMENTAIRE);
        return avisHebergement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvisHebergement createUpdatedEntity(EntityManager em) {
        AvisHebergement avisHebergement = new AvisHebergement()
            .author(UPDATED_AUTHOR)
            .nbreEtoile(UPDATED_NBRE_ETOILE)
            .commentaire(UPDATED_COMMENTAIRE);
        return avisHebergement;
    }

    @BeforeEach
    public void initTest() {
        avisHebergement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvisHebergement() throws Exception {
        int databaseSizeBeforeCreate = avisHebergementRepository.findAll().size();
        // Create the AvisHebergement
        restAvisHebergementMockMvc.perform(post("/api/avis-hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisHebergement)))
            .andExpect(status().isCreated());

        // Validate the AvisHebergement in the database
        List<AvisHebergement> avisHebergementList = avisHebergementRepository.findAll();
        assertThat(avisHebergementList).hasSize(databaseSizeBeforeCreate + 1);
        AvisHebergement testAvisHebergement = avisHebergementList.get(avisHebergementList.size() - 1);
        assertThat(testAvisHebergement.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testAvisHebergement.getNbreEtoile()).isEqualTo(DEFAULT_NBRE_ETOILE);
        assertThat(testAvisHebergement.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void createAvisHebergementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avisHebergementRepository.findAll().size();

        // Create the AvisHebergement with an existing ID
        avisHebergement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvisHebergementMockMvc.perform(post("/api/avis-hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisHebergement)))
            .andExpect(status().isBadRequest());

        // Validate the AvisHebergement in the database
        List<AvisHebergement> avisHebergementList = avisHebergementRepository.findAll();
        assertThat(avisHebergementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAvisHebergements() throws Exception {
        // Initialize the database
        avisHebergementRepository.saveAndFlush(avisHebergement);

        // Get all the avisHebergementList
        restAvisHebergementMockMvc.perform(get("/api/avis-hebergements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avisHebergement.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].nbreEtoile").value(hasItem(DEFAULT_NBRE_ETOILE.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)));
    }
    
    @Test
    @Transactional
    public void getAvisHebergement() throws Exception {
        // Initialize the database
        avisHebergementRepository.saveAndFlush(avisHebergement);

        // Get the avisHebergement
        restAvisHebergementMockMvc.perform(get("/api/avis-hebergements/{id}", avisHebergement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avisHebergement.getId().intValue()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.nbreEtoile").value(DEFAULT_NBRE_ETOILE.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE));
    }
    @Test
    @Transactional
    public void getNonExistingAvisHebergement() throws Exception {
        // Get the avisHebergement
        restAvisHebergementMockMvc.perform(get("/api/avis-hebergements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvisHebergement() throws Exception {
        // Initialize the database
        avisHebergementRepository.saveAndFlush(avisHebergement);

        int databaseSizeBeforeUpdate = avisHebergementRepository.findAll().size();

        // Update the avisHebergement
        AvisHebergement updatedAvisHebergement = avisHebergementRepository.findById(avisHebergement.getId()).get();
        // Disconnect from session so that the updates on updatedAvisHebergement are not directly saved in db
        em.detach(updatedAvisHebergement);
        updatedAvisHebergement
            .author(UPDATED_AUTHOR)
            .nbreEtoile(UPDATED_NBRE_ETOILE)
            .commentaire(UPDATED_COMMENTAIRE);

        restAvisHebergementMockMvc.perform(put("/api/avis-hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvisHebergement)))
            .andExpect(status().isOk());

        // Validate the AvisHebergement in the database
        List<AvisHebergement> avisHebergementList = avisHebergementRepository.findAll();
        assertThat(avisHebergementList).hasSize(databaseSizeBeforeUpdate);
        AvisHebergement testAvisHebergement = avisHebergementList.get(avisHebergementList.size() - 1);
        assertThat(testAvisHebergement.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testAvisHebergement.getNbreEtoile()).isEqualTo(UPDATED_NBRE_ETOILE);
        assertThat(testAvisHebergement.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void updateNonExistingAvisHebergement() throws Exception {
        int databaseSizeBeforeUpdate = avisHebergementRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvisHebergementMockMvc.perform(put("/api/avis-hebergements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisHebergement)))
            .andExpect(status().isBadRequest());

        // Validate the AvisHebergement in the database
        List<AvisHebergement> avisHebergementList = avisHebergementRepository.findAll();
        assertThat(avisHebergementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvisHebergement() throws Exception {
        // Initialize the database
        avisHebergementRepository.saveAndFlush(avisHebergement);

        int databaseSizeBeforeDelete = avisHebergementRepository.findAll().size();

        // Delete the avisHebergement
        restAvisHebergementMockMvc.perform(delete("/api/avis-hebergements/{id}", avisHebergement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AvisHebergement> avisHebergementList = avisHebergementRepository.findAll();
        assertThat(avisHebergementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
