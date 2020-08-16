package ch.itsforward.ecolifeexpedition.web.rest;

import ch.itsforward.ecolifeexpedition.EcoLifeExpeditionApp;
import ch.itsforward.ecolifeexpedition.domain.TypeChambre;
import ch.itsforward.ecolifeexpedition.repository.TypeChambreRepository;

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
 * Integration tests for the {@link TypeChambreResource} REST controller.
 */
@SpringBootTest(classes = EcoLifeExpeditionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TypeChambreResourceIT {

    private static final String DEFAULT_LIBELLE_TYPE_CHAMBRE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_CHAMBRE = "BBBBBBBBBB";

    @Autowired
    private TypeChambreRepository typeChambreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeChambreMockMvc;

    private TypeChambre typeChambre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeChambre createEntity(EntityManager em) {
        TypeChambre typeChambre = new TypeChambre()
            .libelleTypeChambre(DEFAULT_LIBELLE_TYPE_CHAMBRE);
        return typeChambre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeChambre createUpdatedEntity(EntityManager em) {
        TypeChambre typeChambre = new TypeChambre()
            .libelleTypeChambre(UPDATED_LIBELLE_TYPE_CHAMBRE);
        return typeChambre;
    }

    @BeforeEach
    public void initTest() {
        typeChambre = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeChambre() throws Exception {
        int databaseSizeBeforeCreate = typeChambreRepository.findAll().size();
        // Create the TypeChambre
        restTypeChambreMockMvc.perform(post("/api/type-chambres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeChambre)))
            .andExpect(status().isCreated());

        // Validate the TypeChambre in the database
        List<TypeChambre> typeChambreList = typeChambreRepository.findAll();
        assertThat(typeChambreList).hasSize(databaseSizeBeforeCreate + 1);
        TypeChambre testTypeChambre = typeChambreList.get(typeChambreList.size() - 1);
        assertThat(testTypeChambre.getLibelleTypeChambre()).isEqualTo(DEFAULT_LIBELLE_TYPE_CHAMBRE);
    }

    @Test
    @Transactional
    public void createTypeChambreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeChambreRepository.findAll().size();

        // Create the TypeChambre with an existing ID
        typeChambre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeChambreMockMvc.perform(post("/api/type-chambres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeChambre)))
            .andExpect(status().isBadRequest());

        // Validate the TypeChambre in the database
        List<TypeChambre> typeChambreList = typeChambreRepository.findAll();
        assertThat(typeChambreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeChambres() throws Exception {
        // Initialize the database
        typeChambreRepository.saveAndFlush(typeChambre);

        // Get all the typeChambreList
        restTypeChambreMockMvc.perform(get("/api/type-chambres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeChambre.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleTypeChambre").value(hasItem(DEFAULT_LIBELLE_TYPE_CHAMBRE)));
    }
    
    @Test
    @Transactional
    public void getTypeChambre() throws Exception {
        // Initialize the database
        typeChambreRepository.saveAndFlush(typeChambre);

        // Get the typeChambre
        restTypeChambreMockMvc.perform(get("/api/type-chambres/{id}", typeChambre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeChambre.getId().intValue()))
            .andExpect(jsonPath("$.libelleTypeChambre").value(DEFAULT_LIBELLE_TYPE_CHAMBRE));
    }
    @Test
    @Transactional
    public void getNonExistingTypeChambre() throws Exception {
        // Get the typeChambre
        restTypeChambreMockMvc.perform(get("/api/type-chambres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeChambre() throws Exception {
        // Initialize the database
        typeChambreRepository.saveAndFlush(typeChambre);

        int databaseSizeBeforeUpdate = typeChambreRepository.findAll().size();

        // Update the typeChambre
        TypeChambre updatedTypeChambre = typeChambreRepository.findById(typeChambre.getId()).get();
        // Disconnect from session so that the updates on updatedTypeChambre are not directly saved in db
        em.detach(updatedTypeChambre);
        updatedTypeChambre
            .libelleTypeChambre(UPDATED_LIBELLE_TYPE_CHAMBRE);

        restTypeChambreMockMvc.perform(put("/api/type-chambres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeChambre)))
            .andExpect(status().isOk());

        // Validate the TypeChambre in the database
        List<TypeChambre> typeChambreList = typeChambreRepository.findAll();
        assertThat(typeChambreList).hasSize(databaseSizeBeforeUpdate);
        TypeChambre testTypeChambre = typeChambreList.get(typeChambreList.size() - 1);
        assertThat(testTypeChambre.getLibelleTypeChambre()).isEqualTo(UPDATED_LIBELLE_TYPE_CHAMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeChambre() throws Exception {
        int databaseSizeBeforeUpdate = typeChambreRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeChambreMockMvc.perform(put("/api/type-chambres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeChambre)))
            .andExpect(status().isBadRequest());

        // Validate the TypeChambre in the database
        List<TypeChambre> typeChambreList = typeChambreRepository.findAll();
        assertThat(typeChambreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeChambre() throws Exception {
        // Initialize the database
        typeChambreRepository.saveAndFlush(typeChambre);

        int databaseSizeBeforeDelete = typeChambreRepository.findAll().size();

        // Delete the typeChambre
        restTypeChambreMockMvc.perform(delete("/api/type-chambres/{id}", typeChambre.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeChambre> typeChambreList = typeChambreRepository.findAll();
        assertThat(typeChambreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
