package com.example.miProyecto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.miProyecto.model.University;
import com.example.miProyecto.repository.UniversityRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UniversityControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UniversityRepository universityRepository;

    @Test
    public void testGetAllUniversitiesWhenDatabaseNotEmpty() {
        if (universityRepository.findAll().isEmpty()) {
            University testUniversity1 = new University("Nombre 1", "Test Address 1", "123456789", "test1@example.com", true, "Categoria 1", "Test Location 1";
            University testUniversity2 = new University("Nombre 2", "Test Address 2", "123456789", "test1@example.com", true, "Categoria 2", "Test Location 2";;
            universityRepository.save(testUniversity1);
            universityRepository.save(testUniversity2);
        }

        ResponseEntity<University[]> responseEntity = restTemplate.getForEntity("/universities", University[].class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        University[] universities = responseEntity.getBody();
        assertThat(universities).isNotEmpty();
    }

    @Test
    public void testGetAllUniversitiesWhenDatabaseEmpty() {
        universityRepository.deleteAll();

        ResponseEntity<University[]> responseEntity = restTemplate.getForEntity("/universities", University[].class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        University[] universities = responseEntity.getBody();
        assertThat(universities).isEmpty();
    }

    @Test
    public void testCreateUniversityWithScholarshipTrue() {
        University university = new University("Test University", "Test Location", "Test Address", "123456789", "test@example.com", "Test Specialization", true);

        ResponseEntity<University> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/universities", university, University.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        University createdUniversity = responseEntity.getBody();
        assertThat(createdUniversity.getId()).isNotNull();
        assertEquals(university.getName(), createdUniversity.getName());
        assertEquals(university.getLocation(), createdUniversity.getLocation());
        assertEquals(university.isScholarship(), createdUniversity.isScholarship());
    }

    @Test
    public void testCreateUniversityWithScholarshipFalse() {
        University university = new University("Test University", "Test Location", "Test Address", "123456789", "test@example.com", "Test Specialization", false);

        ResponseEntity<University> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/universities", university, University.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        University createdUniversity = responseEntity.getBody();
        assertThat(createdUniversity.getId()).isNotNull();
        assertEquals(university.getName(), createdUniversity.getName());
        assertEquals(university.getLocation(), createdUniversity.getLocation());
        assertEquals(university.isScholarship(), createdUniversity.isScholarship());
    }

    @Test
    public void testUpdateUniversitySuccessful() {
        University existingUniversity = universityRepository.findAll().get(0);
        existingUniversity.setName("Updated University Name");
        restTemplate.put("http://localhost:" + port + "/universities/" + existingUniversity.getId(), existingUniversity);
        University updatedUniversity = universityRepository.findById(existingUniversity.getId()).orElse(null);
        assertThat(updatedUniversity).isNotNull();
        assertEquals(existingUniversity.getName(), updatedUniversity.getName());
    }

    @Test
    public void testUpdateNonExistingUniversity() {
        University nonExistingUniversity = new University("Non Existing University", "Test Location", "Test Address", "123456789", "test@example.com", "Test Specialization", false);

        restTemplate.put("http://localhost:" + port + "/universities/1000", nonExistingUniversity);

        assertTrue(universityRepository.findById(1000L).isEmpty());
    }

    @Test
    public void testDeleteUniversitySuccessful() {
        University existingUniversity = universityRepository.findAll().get(0);
        restTemplate.delete("http://localhost:" + port + "/universities/" + existingUniversity.getId());
        assertTrue(universityRepository.findById(existingUniversity.getId()).isEmpty());
    }

    @Test
    public void testDeleteNonExistingUniversity() {
        restTemplate.delete("http://localhost:" + port + "/universities/1000");

        assertTrue(universityRepository.findById(1000L).isEmpty());
    }
}
