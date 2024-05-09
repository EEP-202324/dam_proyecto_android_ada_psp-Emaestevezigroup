package com.example.miProyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.miProyecto.model.Category;
import com.example.miProyecto.model.Location;
import com.example.miProyecto.model.University;

public interface UniversityRepository extends JpaRepository<University, Long> {
	List<University> findByLocationId(Long locationId);

	List<University> findByCategoryId(Long categoryId);
}
