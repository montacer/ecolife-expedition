package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.DonneurOrdre;
import ch.itsforward.ecolifeexpedition.repository.DonneurOrdreRepository;

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
 * Integration tests for the {@link DonneurOrdreResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DonneurOrdreResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_NUM_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private DonneurOrdreRepository donneurOrdreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDonneurOrdreMockMvc;

    private DonneurOrdre donneurOrdre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonneurOrdre createEntity(EntityManager em) {
        DonneurOrdre donneurOrdre = new DonneurOrdre()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .adresse(DEFAULT_ADRESSE)
            .numTelephone(DEFAULT_NUM_TELEPHONE)
            .email(DEFAULT_EMAIL);
        return donneurOrdre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonneurOrdre createUpdatedEntity(EntityManager em) {
        DonneurOrdre donneurOrdre = new DonneurOrdre()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .adresse(UPDATED_ADRESSE)
            .numTelephone(UPDATED_NUM_TELEPHONE)
            .email(UPDATED_EMAIL);
        return donneurOrdre;
    }

    @BeforeEach
    public void initTest() {
        donneurOrdre = createEntity(em);
    }

    @Test
    @Transactional
    public void createDonneurOrdre() throws Exception {
        int databaseSizeBeforeCreate = donneurOrdreRepository.findAll().size();
        // Create the DonneurOrdre
        restDonneurOrdreMockMvc.perform(post("/api/donneur-ordres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(donneurOrdre)))
            .andExpect(status().isCreated());

        // Validate the DonneurOrdre in the database
        List<DonneurOrdre> donneurOrdreList = donneurOrdreRepository.findAll();
        assertThat(donneurOrdreList).hasSize(databaseSizeBeforeCreate + 1);
        DonneurOrdre testDonneurOrdre = donneurOrdreList.get(donneurOrdreList.size() - 1);
        assertThat(testDonneurOrdre.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDonneurOrdre.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testDonneurOrdre.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testDonneurOrdre.getNumTelephone()).isEqualTo(DEFAULT_NUM_TELEPHONE);
        assertThat(testDonneurOrdre.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createDonneurOrdreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = donneurOrdreRepository.findAll().size();

        // Create the DonneurOrdre with an existing ID
        donneurOrdre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonneurOrdreMockMvc.perform(post("/api/donneur-ordres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(donneurOrdre)))
            .andExpect(status().isBadRequest());

        // Validate the DonneurOrdre in the database
        List<DonneurOrdre> donneurOrdreList = donneurOrdreRepository.findAll();
        assertThat(donneurOrdreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDonneurOrdres() throws Exception {
        // Initialize the database
        donneurOrdreRepository.saveAndFlush(donneurOrdre);

        // Get all the donneurOrdreList
        restDonneurOrdreMockMvc.perform(get("/api/donneur-ordres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donneurOrdre.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].numTelephone").value(hasItem(DEFAULT_NUM_TELEPHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }
    
    @Test
    @Transactional
    public void getDonneurOrdre() throws Exception {
        // Initialize the database
        donneurOrdreRepository.saveAndFlush(donneurOrdre);

        // Get the donneurOrdre
        restDonneurOrdreMockMvc.perform(get("/api/donneur-ordres/{id}", donneurOrdre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(donneurOrdre.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.numTelephone").value(DEFAULT_NUM_TELEPHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }
    @Test
    @Transactional
    public void getNonExistingDonneurOrdre() throws Exception {
        // Get the donneurOrdre
        restDonneurOrdreMockMvc.perform(get("/api/donneur-ordres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDonneurOrdre() throws Exception {
        // Initialize the database
        donneurOrdreRepository.saveAndFlush(donneurOrdre);

        int databaseSizeBeforeUpdate = donneurOrdreRepository.findAll().size();

        // Update the donneurOrdre
        DonneurOrdre updatedDonneurOrdre = donneurOrdreRepository.findById(donneurOrdre.getId()).get();
        // Disconnect from session so that the updates on updatedDonneurOrdre are not directly saved in db
        em.detach(updatedDonneurOrdre);
        updatedDonneurOrdre
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .adresse(UPDATED_ADRESSE)
            .numTelephone(UPDATED_NUM_TELEPHONE)
            .email(UPDATED_EMAIL);

        restDonneurOrdreMockMvc.perform(put("/api/donneur-ordres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDonneurOrdre)))
            .andExpect(status().isOk());

        // Validate the DonneurOrdre in the database
        List<DonneurOrdre> donneurOrdreList = donneurOrdreRepository.findAll();
        assertThat(donneurOrdreList).hasSize(databaseSizeBeforeUpdate);
        DonneurOrdre testDonneurOrdre = donneurOrdreList.get(donneurOrdreList.size() - 1);
        assertThat(testDonneurOrdre.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDonneurOrdre.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDonneurOrdre.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testDonneurOrdre.getNumTelephone()).isEqualTo(UPDATED_NUM_TELEPHONE);
        assertThat(testDonneurOrdre.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingDonneurOrdre() throws Exception {
        int databaseSizeBeforeUpdate = donneurOrdreRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonneurOrdreMockMvc.perform(put("/api/donneur-ordres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(donneurOrdre)))
            .andExpect(status().isBadRequest());

        // Validate the DonneurOrdre in the database
        List<DonneurOrdre> donneurOrdreList = donneurOrdreRepository.findAll();
        assertThat(donneurOrdreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDonneurOrdre() throws Exception {
        // Initialize the database
        donneurOrdreRepository.saveAndFlush(donneurOrdre);

        int databaseSizeBeforeDelete = donneurOrdreRepository.findAll().size();

        // Delete the donneurOrdre
        restDonneurOrdreMockMvc.perform(delete("/api/donneur-ordres/{id}", donneurOrdre.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DonneurOrdre> donneurOrdreList = donneurOrdreRepository.findAll();
        assertThat(donneurOrdreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
