package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.AvisTransfert;
import ch.itsforward.ecolifeexpedition.repository.AvisTransfertRepository;

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
 * Integration tests for the {@link AvisTransfertResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AvisTransfertResourceIT {

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final Stars DEFAULT_NBRE_ETOILE = Stars.ONE;
    private static final Stars UPDATED_NBRE_ETOILE = Stars.TWO;

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    @Autowired
    private AvisTransfertRepository avisTransfertRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvisTransfertMockMvc;

    private AvisTransfert avisTransfert;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvisTransfert createEntity(EntityManager em) {
        AvisTransfert avisTransfert = new AvisTransfert()
            .author(DEFAULT_AUTHOR)
            .nbreEtoile(DEFAULT_NBRE_ETOILE)
            .commentaire(DEFAULT_COMMENTAIRE);
        return avisTransfert;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvisTransfert createUpdatedEntity(EntityManager em) {
        AvisTransfert avisTransfert = new AvisTransfert()
            .author(UPDATED_AUTHOR)
            .nbreEtoile(UPDATED_NBRE_ETOILE)
            .commentaire(UPDATED_COMMENTAIRE);
        return avisTransfert;
    }

    @BeforeEach
    public void initTest() {
        avisTransfert = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvisTransfert() throws Exception {
        int databaseSizeBeforeCreate = avisTransfertRepository.findAll().size();
        // Create the AvisTransfert
        restAvisTransfertMockMvc.perform(post("/api/avis-transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisTransfert)))
            .andExpect(status().isCreated());

        // Validate the AvisTransfert in the database
        List<AvisTransfert> avisTransfertList = avisTransfertRepository.findAll();
        assertThat(avisTransfertList).hasSize(databaseSizeBeforeCreate + 1);
        AvisTransfert testAvisTransfert = avisTransfertList.get(avisTransfertList.size() - 1);
        assertThat(testAvisTransfert.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testAvisTransfert.getNbreEtoile()).isEqualTo(DEFAULT_NBRE_ETOILE);
        assertThat(testAvisTransfert.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void createAvisTransfertWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avisTransfertRepository.findAll().size();

        // Create the AvisTransfert with an existing ID
        avisTransfert.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvisTransfertMockMvc.perform(post("/api/avis-transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisTransfert)))
            .andExpect(status().isBadRequest());

        // Validate the AvisTransfert in the database
        List<AvisTransfert> avisTransfertList = avisTransfertRepository.findAll();
        assertThat(avisTransfertList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAvisTransferts() throws Exception {
        // Initialize the database
        avisTransfertRepository.saveAndFlush(avisTransfert);

        // Get all the avisTransfertList
        restAvisTransfertMockMvc.perform(get("/api/avis-transferts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avisTransfert.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].nbreEtoile").value(hasItem(DEFAULT_NBRE_ETOILE.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)));
    }
    
    @Test
    @Transactional
    public void getAvisTransfert() throws Exception {
        // Initialize the database
        avisTransfertRepository.saveAndFlush(avisTransfert);

        // Get the avisTransfert
        restAvisTransfertMockMvc.perform(get("/api/avis-transferts/{id}", avisTransfert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avisTransfert.getId().intValue()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.nbreEtoile").value(DEFAULT_NBRE_ETOILE.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE));
    }
    @Test
    @Transactional
    public void getNonExistingAvisTransfert() throws Exception {
        // Get the avisTransfert
        restAvisTransfertMockMvc.perform(get("/api/avis-transferts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvisTransfert() throws Exception {
        // Initialize the database
        avisTransfertRepository.saveAndFlush(avisTransfert);

        int databaseSizeBeforeUpdate = avisTransfertRepository.findAll().size();

        // Update the avisTransfert
        AvisTransfert updatedAvisTransfert = avisTransfertRepository.findById(avisTransfert.getId()).get();
        // Disconnect from session so that the updates on updatedAvisTransfert are not directly saved in db
        em.detach(updatedAvisTransfert);
        updatedAvisTransfert
            .author(UPDATED_AUTHOR)
            .nbreEtoile(UPDATED_NBRE_ETOILE)
            .commentaire(UPDATED_COMMENTAIRE);

        restAvisTransfertMockMvc.perform(put("/api/avis-transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvisTransfert)))
            .andExpect(status().isOk());

        // Validate the AvisTransfert in the database
        List<AvisTransfert> avisTransfertList = avisTransfertRepository.findAll();
        assertThat(avisTransfertList).hasSize(databaseSizeBeforeUpdate);
        AvisTransfert testAvisTransfert = avisTransfertList.get(avisTransfertList.size() - 1);
        assertThat(testAvisTransfert.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testAvisTransfert.getNbreEtoile()).isEqualTo(UPDATED_NBRE_ETOILE);
        assertThat(testAvisTransfert.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void updateNonExistingAvisTransfert() throws Exception {
        int databaseSizeBeforeUpdate = avisTransfertRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvisTransfertMockMvc.perform(put("/api/avis-transferts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisTransfert)))
            .andExpect(status().isBadRequest());

        // Validate the AvisTransfert in the database
        List<AvisTransfert> avisTransfertList = avisTransfertRepository.findAll();
        assertThat(avisTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvisTransfert() throws Exception {
        // Initialize the database
        avisTransfertRepository.saveAndFlush(avisTransfert);

        int databaseSizeBeforeDelete = avisTransfertRepository.findAll().size();

        // Delete the avisTransfert
        restAvisTransfertMockMvc.perform(delete("/api/avis-transferts/{id}", avisTransfert.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AvisTransfert> avisTransfertList = avisTransfertRepository.findAll();
        assertThat(avisTransfertList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
