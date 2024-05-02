package com.example.miProyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.miProyecto.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
