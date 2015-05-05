package fr.istic.sir.web.rest;

import fr.istic.sir.Application;
import fr.istic.sir.domain.Maison;
import fr.istic.sir.repository.MaisonRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MaisonResource REST controller.
 *
 * @see MaisonResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MaisonResourceTest {

    private static final String DEFAULT_ADRESSE = "SAMPLE_TEXT";
    private static final String UPDATED_ADRESSE = "UPDATED_TEXT";
    private static final String DEFAULT_ADRESSEIP = "SAMPLE_TEXT";
    private static final String UPDATED_ADRESSEIP = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_SUPERFICIE = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_SUPERFICIE = BigDecimal.ONE;

    @Inject
    private MaisonRepository maisonRepository;

    private MockMvc restMaisonMockMvc;

    private Maison maison;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MaisonResource maisonResource = new MaisonResource();
        ReflectionTestUtils.setField(maisonResource, "maisonRepository", maisonRepository);
        this.restMaisonMockMvc = MockMvcBuilders.standaloneSetup(maisonResource).build();
    }

    @Before
    public void initTest() {
        maison = new Maison();
        maison.setAdresse(DEFAULT_ADRESSE);
        maison.setAdresseip(DEFAULT_ADRESSEIP);
        maison.setSuperficie(DEFAULT_SUPERFICIE);
    }

    @Test
    @Transactional
    public void createMaison() throws Exception {
        // Validate the database is empty
        assertThat(maisonRepository.findAll()).hasSize(0);

        // Create the Maison
        restMaisonMockMvc.perform(post("/api/maisons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(maison)))
                .andExpect(status().isCreated());

        // Validate the Maison in the database
        List<Maison> maisons = maisonRepository.findAll();
        assertThat(maisons).hasSize(1);
        Maison testMaison = maisons.iterator().next();
        assertThat(testMaison.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testMaison.getAdresseip()).isEqualTo(DEFAULT_ADRESSEIP);
        assertThat(testMaison.getSuperficie()).isEqualTo(DEFAULT_SUPERFICIE);
    }

    @Test
    @Transactional
    public void getAllMaisons() throws Exception {
        // Initialize the database
        maisonRepository.saveAndFlush(maison);

        // Get all the maisons
        restMaisonMockMvc.perform(get("/api/maisons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(maison.getId().intValue()))
                .andExpect(jsonPath("$.[0].adresse").value(DEFAULT_ADRESSE.toString()))
                .andExpect(jsonPath("$.[0].adresseip").value(DEFAULT_ADRESSEIP.toString()))
                .andExpect(jsonPath("$.[0].superficie").value(DEFAULT_SUPERFICIE.intValue()));
    }

    @Test
    @Transactional
    public void getMaison() throws Exception {
        // Initialize the database
        maisonRepository.saveAndFlush(maison);

        // Get the maison
        restMaisonMockMvc.perform(get("/api/maisons/{id}", maison.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(maison.getId().intValue()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.adresseip").value(DEFAULT_ADRESSEIP.toString()))
            .andExpect(jsonPath("$.superficie").value(DEFAULT_SUPERFICIE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMaison() throws Exception {
        // Get the maison
        restMaisonMockMvc.perform(get("/api/maisons/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaison() throws Exception {
        // Initialize the database
        maisonRepository.saveAndFlush(maison);

        // Update the maison
        maison.setAdresse(UPDATED_ADRESSE);
        maison.setAdresseip(UPDATED_ADRESSEIP);
        maison.setSuperficie(UPDATED_SUPERFICIE);
        restMaisonMockMvc.perform(put("/api/maisons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(maison)))
                .andExpect(status().isOk());

        // Validate the Maison in the database
        List<Maison> maisons = maisonRepository.findAll();
        assertThat(maisons).hasSize(1);
        Maison testMaison = maisons.iterator().next();
        assertThat(testMaison.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testMaison.getAdresseip()).isEqualTo(UPDATED_ADRESSEIP);
        assertThat(testMaison.getSuperficie()).isEqualTo(UPDATED_SUPERFICIE);
    }

    @Test
    @Transactional
    public void deleteMaison() throws Exception {
        // Initialize the database
        maisonRepository.saveAndFlush(maison);

        // Get the maison
        restMaisonMockMvc.perform(delete("/api/maisons/{id}", maison.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Maison> maisons = maisonRepository.findAll();
        assertThat(maisons).hasSize(0);
    }
}
