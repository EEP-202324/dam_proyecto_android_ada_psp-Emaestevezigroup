package com.example.miProyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.miProyecto.model.Category;
import com.example.miProyecto.model.Location;
import com.example.miProyecto.model.University;
import com.example.miProyecto.repository.UniversityRepository;

@Service
public class UniversityService {
	@Autowired
	private UniversityRepository universityRepository; 

	public University saveUniversity(University university) {
		if (university.getPhoneNumber().length() != 9) {
			throw new IllegalArgumentException("El número de teléfono debe tener exactamente 9 cifras.");
		}
		return universityRepository.save(university);
	}

	public List<University> getAllUniversities() {
		return universityRepository.findAll();
	}

	public Optional<University> getUniversityById(Long id) {
		return universityRepository.findById(id);
	}

	public boolean deleteUniversity(Long id) {
		if (universityRepository.existsById(id)) {
			universityRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	public List<University> getUniversitiesByCategory(Long categoryId) {
		return universityRepository.findByCategoryId(categoryId);
	}

	public List<University> getUniversitiesByLocation(Long locationId) {
		return universityRepository.findByLocationId(locationId);
	}
}
