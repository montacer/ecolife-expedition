package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.AvisTour;
import ch.itsforward.ecolifeexpedition.repository.AvisTourRepository;

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
 * Integration tests for the {@link AvisTourResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AvisTourResourceIT {

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final Stars DEFAULT_NBRE_ETOILE = Stars.ONE;
    private static final Stars UPDATED_NBRE_ETOILE = Stars.TWO;

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    @Autowired
    private AvisTourRepository avisTourRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvisTourMockMvc;

    private AvisTour avisTour;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvisTour createEntity(EntityManager em) {
        AvisTour avisTour = new AvisTour()
            .author(DEFAULT_AUTHOR)
            .nbreEtoile(DEFAULT_NBRE_ETOILE)
            .commentaire(DEFAULT_COMMENTAIRE);
        return avisTour;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvisTour createUpdatedEntity(EntityManager em) {
        AvisTour avisTour = new AvisTour()
            .author(UPDATED_AUTHOR)
            .nbreEtoile(UPDATED_NBRE_ETOILE)
            .commentaire(UPDATED_COMMENTAIRE);
        return avisTour;
    }

    @BeforeEach
    public void initTest() {
        avisTour = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvisTour() throws Exception {
        int databaseSizeBeforeCreate = avisTourRepository.findAll().size();
        // Create the AvisTour
        restAvisTourMockMvc.perform(post("/api/avis-tours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisTour)))
            .andExpect(status().isCreated());

        // Validate the AvisTour in the database
        List<AvisTour> avisTourList = avisTourRepository.findAll();
        assertThat(avisTourList).hasSize(databaseSizeBeforeCreate + 1);
        AvisTour testAvisTour = avisTourList.get(avisTourList.size() - 1);
        assertThat(testAvisTour.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testAvisTour.getNbreEtoile()).isEqualTo(DEFAULT_NBRE_ETOILE);
        assertThat(testAvisTour.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void createAvisTourWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avisTourRepository.findAll().size();

        // Create the AvisTour with an existing ID
        avisTour.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvisTourMockMvc.perform(post("/api/avis-tours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisTour)))
            .andExpect(status().isBadRequest());

        // Validate the AvisTour in the database
        List<AvisTour> avisTourList = avisTourRepository.findAll();
        assertThat(avisTourList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAvisTours() throws Exception {
        // Initialize the database
        avisTourRepository.saveAndFlush(avisTour);

        // Get all the avisTourList
        restAvisTourMockMvc.perform(get("/api/avis-tours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avisTour.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].nbreEtoile").value(hasItem(DEFAULT_NBRE_ETOILE.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)));
    }
    
    @Test
    @Transactional
    public void getAvisTour() throws Exception {
        // Initialize the database
        avisTourRepository.saveAndFlush(avisTour);

        // Get the avisTour
        restAvisTourMockMvc.perform(get("/api/avis-tours/{id}", avisTour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avisTour.getId().intValue()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.nbreEtoile").value(DEFAULT_NBRE_ETOILE.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE));
    }
    @Test
    @Transactional
    public void getNonExistingAvisTour() throws Exception {
        // Get the avisTour
        restAvisTourMockMvc.perform(get("/api/avis-tours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvisTour() throws Exception {
        // Initialize the database
        avisTourRepository.saveAndFlush(avisTour);

        int databaseSizeBeforeUpdate = avisTourRepository.findAll().size();

        // Update the avisTour
        AvisTour updatedAvisTour = avisTourRepository.findById(avisTour.getId()).get();
        // Disconnect from session so that the updates on updatedAvisTour are not directly saved in db
        em.detach(updatedAvisTour);
        updatedAvisTour
            .author(UPDATED_AUTHOR)
            .nbreEtoile(UPDATED_NBRE_ETOILE)
            .commentaire(UPDATED_COMMENTAIRE);

        restAvisTourMockMvc.perform(put("/api/avis-tours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvisTour)))
            .andExpect(status().isOk());

        // Validate the AvisTour in the database
        List<AvisTour> avisTourList = avisTourRepository.findAll();
        assertThat(avisTourList).hasSize(databaseSizeBeforeUpdate);
        AvisTour testAvisTour = avisTourList.get(avisTourList.size() - 1);
        assertThat(testAvisTour.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testAvisTour.getNbreEtoile()).isEqualTo(UPDATED_NBRE_ETOILE);
        assertThat(testAvisTour.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void updateNonExistingAvisTour() throws Exception {
        int databaseSizeBeforeUpdate = avisTourRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvisTourMockMvc.perform(put("/api/avis-tours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avisTour)))
            .andExpect(status().isBadRequest());

        // Validate the AvisTour in the database
        List<AvisTour> avisTourList = avisTourRepository.findAll();
        assertThat(avisTourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvisTour() throws Exception {
        // Initialize the database
        avisTourRepository.saveAndFlush(avisTour);

        int databaseSizeBeforeDelete = avisTourRepository.findAll().size();

        // Delete the avisTour
        restAvisTourMockMvc.perform(delete("/api/avis-tours/{id}", avisTour.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AvisTour> avisTourList = avisTourRepository.findAll();
        assertThat(avisTourList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
