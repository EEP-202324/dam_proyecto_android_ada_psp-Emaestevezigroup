package com.example.miProyecto.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import com.example.miProyecto.model.Category;
import com.example.miProyecto.model.Location;
import com.example.miProyecto.model.University;
import com.example.miProyecto.repository.UniversityRepository;
import com.example.miProyecto.service.UniversityService;

import jakarta.validation.Valid;


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

	 @GetMapping("/byLocation/{id}")
	    public List<University> getUniversitiesByLocation(@PathVariable Long id) {
	        return universityService.getUniversitiesByLocation(id);
	    }
	 
	 @GetMapping("/byCategory/{id}")
	    public List<University> getUniversitiesByCategory(@PathVariable Long id) {
	        return universityService.getUniversitiesByCategory(id);
	    }
	 

	    @PostMapping
	    public ResponseEntity<?> createUniversity(@Valid @RequestBody University university, BindingResult bindingResult) {
	        if (bindingResult.hasErrors()) {
	            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
	        }
	        
	        String phoneNumber = university.getPhoneNumber();
	        if (phoneNumber != null && phoneNumber.length() != 9) {
	            return ResponseEntity.badRequest().body("El número de teléfono debe tener 9 dígitos");
	        }
	        
	        String email = university.getEmail();
	        if (!isValidEmail(email)) {
	            return ResponseEntity.badRequest().body("El email debe tener @ y .extensión");
	        }

	        University createdUniversity = universityRepository.save(university);
	        return ResponseEntity.ok(createdUniversity);
	    }
	 

		@PutMapping("/{id}")
		public ResponseEntity<University> updateUniversity(@PathVariable Long id, @RequestBody University universityDetails) {
		    Optional<University> optionalUniversity = universityRepository.findById(id);
		    if (optionalUniversity.isPresent()) {
		        University university = optionalUniversity.get();
		        university.setName(universityDetails.getName());
		        university.setLocation(universityDetails.getLocation());
		        university.setScholarship(universityDetails.isScholarship());

		        University updatedUniversity = universityRepository.save(university);
		        return ResponseEntity.ok(updatedUniversity);
		    } else {
		        return ResponseEntity.notFound().build();
		    }
		}


	 
		@DeleteMapping("/{id}")
		public ResponseEntity<?> deleteUniversity(@PathVariable Long id) {
		    Optional<University> optionalUniversity = universityRepository.findById(id);
		    if (optionalUniversity.isPresent()) {
		        University university = optionalUniversity.get();
		        universityRepository.delete(university);
		        return ResponseEntity.ok().build();
		    } else {
		        return ResponseEntity.notFound().build();
		    }
		}

		private boolean isValidEmail(String email) {
	        return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
	    }


}
