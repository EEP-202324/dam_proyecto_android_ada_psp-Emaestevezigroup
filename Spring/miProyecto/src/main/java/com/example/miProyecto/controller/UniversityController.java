package com.example.miProyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.miProyecto.exception.ResourceNotFoundException;
import com.example.miProyecto.model.University;
import com.example.miProyecto.repository.UniversityRepository;
import com.example.miProyecto.service.UniversityService;

@RestController
@RequestMapping("/universities")
public class UniversityController {

	private UniversityRepository universityRepository;
	private UniversityService universityService;

	@Autowired
	public UniversityController(UniversityRepository universityRepository, UniversityService universityService) {
		this.universityRepository = universityRepository;
		this.universityService = universityService;
	}

	@GetMapping
	public ResponseEntity<List<University>> getAllUniversities(Pageable pageable) {
		Page<University> page = universityRepository.findAll(PageRequest.of(pageable.getPageNumber(),
				pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Direction.ASC, "name"))));
		return ResponseEntity.ok(page.getContent());
	}

	@GetMapping("/{id}")
	public ResponseEntity<University> getUniversityById(@PathVariable Long id) {
		University university = universityRepository.findById(id).orElse(null);

		if (university != null) {
			return ResponseEntity.ok(university);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	 @GetMapping("/byLocation")
	    public List<University> getUniversitiesByLocation(@RequestParam String location) {
	        return universityService.getUniversitiesByLocation(location);
	    }
	 
	 @GetMapping("/byCategory")
	    public List<University> getUniversitiesByCategory(@RequestParam String category) {
	        return universityService.getUniversitiesByCategory(category);
	    }
	 

		@PostMapping
		public University createUniversity(@RequestBody University university) {
			return universityRepository.save(university);
		}
	 

		@PutMapping("/{id}")
		public University updateUniversity(@PathVariable Long id, @RequestBody University universityDetails) {
			University university = universityRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("University not found with id " + id));

			university.setName(universityDetails.getName());
			university.setLocation(universityDetails.getLocation());
			university.setScholarship(universityDetails.isScholarship());

			return universityRepository.save(university);
		}
	 
	 @DeleteMapping("/{id}")
		public ResponseEntity<?> deleteUniversity(@PathVariable Long id) {
			University university = universityRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("University not found with id " + id));

			universityRepository.delete(university);

			return ResponseEntity.ok().build();
		}
}
