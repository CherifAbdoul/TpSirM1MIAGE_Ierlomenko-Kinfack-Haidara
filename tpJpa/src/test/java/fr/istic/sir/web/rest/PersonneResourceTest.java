package fr.istic.sir.web.rest;

import fr.istic.sir.Application;
import fr.istic.sir.domain.Person;
import fr.istic.sir.repository.PersonneRepository;

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
import org.joda.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PersonneResource REST controller.
 *
 * @see PersonneResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PersonneResourceTest {

    private static final String DEFAULT_NOM = "SAMPLE_TEXT";
    private static final String UPDATED_NOM = "UPDATED_TEXT";
    private static final String DEFAULT_PRENOM = "SAMPLE_TEXT";
    private static final String UPDATED_PRENOM = "UPDATED_TEXT";

    private static final LocalDate DEFAULT_DATENAIS = new LocalDate(0L);
    private static final LocalDate UPDATED_DATENAIS = new LocalDate();
    private static final String DEFAULT_MAIL = "SAMPLE_TEXT";
    private static final String UPDATED_MAIL = "UPDATED_TEXT";
    private static final String DEFAULT_PROFIL = "SAMPLE_TEXT";
    private static final String UPDATED_PROFIL = "UPDATED_TEXT";

    @Inject
    private PersonneRepository personneRepository;

    private MockMvc restPersonneMockMvc;

    private Person personne;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonneResource personneResource = new PersonneResource();
        ReflectionTestUtils.setField(personneResource, "personneRepository", personneRepository);
        this.restPersonneMockMvc = MockMvcBuilders.standaloneSetup(personneResource).build();
    }

    @Before
    public void initTest() {
        personne = new Person();
        personne.setNom(DEFAULT_NOM);
        personne.setPrenom(DEFAULT_PRENOM);
        personne.setDatenais(DEFAULT_DATENAIS);
        personne.setMail(DEFAULT_MAIL);
        personne.setProfil(DEFAULT_PROFIL);
    }

    @Test
    @Transactional
    public void createPersonne() throws Exception {
        // Validate the database is empty
        assertThat(personneRepository.findAll()).hasSize(0);

        // Create the Personne
        restPersonneMockMvc.perform(post("/api/personnes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personne)))
                .andExpect(status().isCreated());

        // Validate the Personne in the database
        List<Person> personnes = personneRepository.findAll();
        assertThat(personnes).hasSize(1);
        Person testPersonne = personnes.iterator().next();
        assertThat(testPersonne.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPersonne.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testPersonne.getDatenais()).isEqualTo(DEFAULT_DATENAIS);
        assertThat(testPersonne.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testPersonne.getProfil()).isEqualTo(DEFAULT_PROFIL);
    }

    @Test
    @Transactional
    public void getAllPersonnes() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Get all the personnes
        restPersonneMockMvc.perform(get("/api/personnes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(personne.getId().intValue()))
                .andExpect(jsonPath("$.[0].nom").value(DEFAULT_NOM.toString()))
                .andExpect(jsonPath("$.[0].prenom").value(DEFAULT_PRENOM.toString()))
                .andExpect(jsonPath("$.[0].datenais").value(DEFAULT_DATENAIS.toString()))
                .andExpect(jsonPath("$.[0].mail").value(DEFAULT_MAIL.toString()))
                .andExpect(jsonPath("$.[0].profil").value(DEFAULT_PROFIL.toString()));
    }

    @Test
    @Transactional
    public void getPersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Get the personne
        restPersonneMockMvc.perform(get("/api/personnes/{id}", personne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(personne.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.datenais").value(DEFAULT_DATENAIS.toString()))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL.toString()))
            .andExpect(jsonPath("$.profil").value(DEFAULT_PROFIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonne() throws Exception {
        // Get the personne
        restPersonneMockMvc.perform(get("/api/personnes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Update the personne
        personne.setNom(UPDATED_NOM);
        personne.setPrenom(UPDATED_PRENOM);
        personne.setDatenais(UPDATED_DATENAIS);
        personne.setMail(UPDATED_MAIL);
        personne.setProfil(UPDATED_PROFIL);
        restPersonneMockMvc.perform(put("/api/personnes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personne)))
                .andExpect(status().isOk());

        // Validate the Personne in the database
        List<Person> personnes = personneRepository.findAll();
        assertThat(personnes).hasSize(1);
        Person testPersonne = personnes.iterator().next();
        assertThat(testPersonne.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPersonne.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPersonne.getDatenais()).isEqualTo(UPDATED_DATENAIS);
        assertThat(testPersonne.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testPersonne.getProfil()).isEqualTo(UPDATED_PROFIL);
    }

    @Test
    @Transactional
    public void deletePersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Get the personne
        restPersonneMockMvc.perform(delete("/api/personnes/{id}", personne.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Person> personnes = personneRepository.findAll();
        assertThat(personnes).hasSize(0);
    }
}
