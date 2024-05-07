package com.example.miProyecto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.miProyecto.model.Category;
import com.example.miProyecto.model.Location;
import com.example.miProyecto.model.University;
import com.example.miProyecto.repository.UniversityRepository;
import com.example.miProyecto.service.UniversityService;

public class UniversityServiceTests {

    @Mock
    private UniversityRepository universityRepository;

    @InjectMocks
    private UniversityService universityService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUniversitiesByLocation() {
        Location location = new Location("Location A");
        List<University> universities = new ArrayList<>();
        universities.add(new University("University A", "Address A", "123456", "email@example.com", true, new Category("Category A"), location));
        universities.add(new University("University B", "Address B", "789012", "email@example.com", false, new Category("Category B"), location));

        when(universityRepository.findByLocation(location)).thenReturn(universities);

        List<University> result = universityService.getUniversitiesByLocation(location);

        assertEquals(2, result.size());
        assertEquals("University A", result.get(0).getName());
        assertEquals("University B", result.get(1).getName());
    }

    @Test
    public void testGetUniversitiesByCategory() {
        Category category = new Category("Category A");
        List<University> universities = new ArrayList<>();
        universities.add(new University("University A", "Address A", "123456", "email@example.com", true, category, new Location("Location A")));
        universities.add(new University("University B", "Address B", "789012", "email@example.com", false, category, new Location("Location B")));

        when(universityRepository.findByCategory(category)).thenReturn(universities);

        List<University> result = universityService.getUniversitiesByCategory(category);

        assertEquals(2, result.size());
        assertEquals("University A", result.get(0).getName());
        assertEquals("University B", result.get(1).getName());
    }

    @Test
    public void testGetUniversityById() {
        Long id = 1L;
        University university = new University("University A", "Address A", "123456", "email@example.com", true, new Category("Category A"), new Location("Location A"));
        university.setId(id);

        when(universityRepository.findById(id)).thenReturn(Optional.of(university));

        Optional<University> result = universityService.getUniversityById(id);

        assertEquals("University A", result.get().getName());
        assertEquals(id, result.get().getId());
    }
}
