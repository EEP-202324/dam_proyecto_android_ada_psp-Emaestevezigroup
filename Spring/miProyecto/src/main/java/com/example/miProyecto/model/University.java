package com.example.miProyecto.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class University {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String location; 
	private String address;
	private String phoneNumber;
	private String email; 
	private String specialization;  
	private boolean scholarship; 
	
	private boolean scholarships = false;

	public University() {
	}

	public University(String name, String location, String address, String phoneNumber, String email,
			String specialization, boolean scholarship) {
		this.name = name;
		this.location = location;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.specialization = specialization;
		this.scholarship = scholarship;
	}
	
	 @ManyToMany
	    @JoinTable(
	        name = "university_category",
	        joinColumns = @JoinColumn(name = "university_id"),
	        inverseJoinColumns = @JoinColumn(name = "category_id")
	    )
	    private Set<Category> categories;

	    @ManyToMany
	    @JoinTable(
	        name = "university_location",
	        joinColumns = @JoinColumn(name = "university_id"),
	        inverseJoinColumns = @JoinColumn(name = "location_id")
	    )

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public boolean isScholarship() {
		return scholarship;
	}

	public void setScholarship(boolean scholarship) {
		this.scholarship = scholarship;
	}
}
