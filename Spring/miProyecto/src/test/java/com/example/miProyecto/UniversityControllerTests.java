package com.example.miProyecto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult; // Importa BindingResult de la ubicación correcta

import com.example.miProyecto.controller.UniversityController;
import com.example.miProyecto.exception.ResourceNotFoundException;
import com.example.miProyecto.model.Category;
import com.example.miProyecto.model.Location;
import com.example.miProyecto.model.University;
import com.example.miProyecto.repository.UniversityRepository;
import com.example.miProyecto.service.UniversityService;

public class UniversityControllerTests {

    @Mock
    private UniversityRepository universityRepository;

    @Mock
    private UniversityService universityService;

    @InjectMocks
    private UniversityController universityController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUniversities() {
        List<University> universities = new ArrayList<>();
        universities.add(new University("University A", "Address A", "123456", "email@example.com", true, new Category("Category A"), new Location("Location A")));
        universities.add(new University("University B", "Address B", "789012", "email@example.com", false, new Category("Category B"), new Location("Location B")));
        PageImpl<University> page = new PageImpl<>(universities);

        when(universityRepository.findAll(any(PageRequest.class))).thenReturn(page);

        ResponseEntity<List<University>> response = universityController.getAllUniversities(PageRequest.of(0, 10));
        List<University> result = response.getBody();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetUniversityById() {
        Long id = 1L;
        University university = new University();
        university.setId(id);

        when(universityRepository.findById(id)).thenReturn(Optional.of(university));

        ResponseEntity<University> response = universityController.getUniversityById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(university, response.getBody());
    }

    @Test
    public void testGetUniversityById_NotFound() {
        Long id = 1L;

        when(universityRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<University> response = universityController.getUniversityById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateUniversity() {
        University university = new University();
        university.setName("Test University");
        university.setPhoneNumber("123456789");

        when(universityRepository.save(any())).thenReturn(university);

        ResponseEntity<?> response = universityController.createUniversity(university, mock(BindingResult.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateUniversity_Error() {
        University university = new University();
        university.setName("Test University");
        university.setPhoneNumber("123");

        ResponseEntity<?> response = universityController.createUniversity(university, mock(BindingResult.class));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdateUniversity() {
        Long id = 1L;
        University existingUniversity = new University();
        existingUniversity.setId(id);
        existingUniversity.setName("Existing University");

        University updatedUniversity = new University();
        updatedUniversity.setId(id);
        updatedUniversity.setName("Updated University");

        when(universityRepository.findById(id)).thenReturn(Optional.of(existingUniversity));
        when(universityRepository.save(any())).thenReturn(updatedUniversity);

        ResponseEntity<University> result = universityController.updateUniversity(id, updatedUniversity);
        University resultUniversity = result.getBody();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(updatedUniversity, resultUniversity);
    }


    @Test
    public void testUpdateUniversity_NotFound() {
        Long id = 1L;
        University updatedUniversity = new University();
        updatedUniversity.setId(id);
        updatedUniversity.setName("Updated University");

        when(universityRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<University> response = universityController.updateUniversity(id, updatedUniversity);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }




    @Test
    public void testDeleteUniversity() {
        Long id = 1L;
        University university = new University();
        university.setId(id);

        when(universityRepository.findById(id)).thenReturn(Optional.of(university));

        ResponseEntity<?> response = universityController.deleteUniversity(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(universityRepository, times(1)).delete(university);
    }

    @Test
    public void testDeleteUniversity_NotFound() {
        Long id = 1L;

        when(universityRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = universityController.deleteUniversity(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody()); 
        verify(universityRepository, never()).delete(any());
    }

}
