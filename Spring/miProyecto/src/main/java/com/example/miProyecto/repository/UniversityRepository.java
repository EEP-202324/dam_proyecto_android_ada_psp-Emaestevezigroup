package com.example.miProyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.miProyecto.model.University;

public interface UniversityRepository extends JpaRepository<University, Long> {
    List<University> findByLocation(String location);

	List<University> findBySpecialization(String category);
}




