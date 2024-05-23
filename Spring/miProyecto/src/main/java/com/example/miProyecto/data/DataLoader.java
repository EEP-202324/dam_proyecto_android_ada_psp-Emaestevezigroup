package com.example.miProyecto.data;

import java.util.ArrayList;
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

	private List<Category> loadedCategories = new ArrayList<>();
	private List<Location> loadedLocations = new ArrayList<>();

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
			Category ingenieriaCategory = new Category("Ingenieria");
			Category arteCategory = new Category("Arte");
			Category saludCategory = new Category("Salud");
			Category cienciasCategory = new Category("Ciencias");
			Category humanidadesCategory = new Category("Letras");

			loadedCategories = categoryRepository.saveAll(
					List.of(ingenieriaCategory, arteCategory, saludCategory, cienciasCategory, humanidadesCategory));
		}
	}

	private void loadLocations() {
		if (locationRepository.count() == 0) {
			Location madridLocation = new Location("Madrid");
			Location barcelonaLocation = new Location("Barcelona");
			Location sevillaLocation = new Location("Sevilla");
			Location valenciaLocation = new Location("Valencia");
			Location malagaLocation = new Location("Malaga");

			loadedLocations = locationRepository.saveAll(
					List.of(madridLocation, barcelonaLocation, sevillaLocation, valenciaLocation, malagaLocation));
		}
	}

	public List<Category> getLoadedCategories() {
		return loadedCategories;
	}

	public List<Location> getLoadedLocations() {
		return loadedLocations;
	}
}
