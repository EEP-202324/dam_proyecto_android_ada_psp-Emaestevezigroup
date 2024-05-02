package com.example.miProyecto.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.miProyecto.model.Category;
import com.example.miProyecto.model.Location;
import com.example.miProyecto.repository.CategoryRepository;
import com.example.miProyecto.repository.LocationRepository;

@Component
public class DataLoader implements CommandLineRunner {

	private final CategoryRepository categoryRepository;
	private final LocationRepository locationRepository;

	@Autowired
	public DataLoader(CategoryRepository categoryRepository, LocationRepository locationRepository) {
		this.categoryRepository = categoryRepository;
		this.locationRepository = locationRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		loadCategories();

		loadLocations();
	}

	private void loadCategories() {
		if (categoryRepository.count() == 0) {
			Category engineeringCategory = new Category("Ingenieria");
			Category artsCategory = new Category("Arte");
			Category healthCategory = new Category("Salud");
			Category sciencesCategory = new Category("Ciencias");
			Category humanitiesCategory = new Category("Letras");

			categoryRepository.saveAll(
					List.of(engineeringCategory, artsCategory, healthCategory, sciencesCategory, humanitiesCategory));
		}
	}

	private void loadLocations() {
		if (locationRepository.count() == 0) {
			Location madridLocation = new Location("Madrid");
			Location barcelonaLocation = new Location("Barcelona");
			Location sevilleLocation = new Location("Sevilla");
			Location valenciaLocation = new Location("Valencia");
			Location malagaLocation = new Location("Malaga");

			locationRepository.saveAll(
					List.of(madridLocation, barcelonaLocation, sevilleLocation, valenciaLocation, malagaLocation));
		}
	}
}
