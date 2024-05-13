package com.example.miProyecto.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.miProyecto.model.Category;
import com.example.miProyecto.repository.CategoryRepository;

public class CategoryController {

	private CategoryRepository categoryRepository;

	@GetMapping
	public ResponseEntity<List<Category>> getAllCategory() {
		List<Category> page = categoryRepository.findAll();
		return ResponseEntity.ok(page);
	}

}
