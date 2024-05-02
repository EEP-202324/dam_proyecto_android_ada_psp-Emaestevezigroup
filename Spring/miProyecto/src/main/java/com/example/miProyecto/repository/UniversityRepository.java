package com.example.miProyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.miProyecto.model.University;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    List<University> findBySpecialization(String specialization);
}



