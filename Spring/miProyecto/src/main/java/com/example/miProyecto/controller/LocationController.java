package com.example.miProyecto.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.miProyecto.model.Location;
import com.example.miProyecto.repository.LocationRepository;

public class LocationController {

	private LocationRepository locationRepository;

	@GetMapping
	public ResponseEntity<List<Location>> getAllLocation() {
		List<Location> page = locationRepository.findAll();
		return ResponseEntity.ok(page);
	}

}
