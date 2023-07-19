package com.hdekker.calendarhome;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TestEntity{
	
	Long id;
	
	String name;
	
	public TestEntity() {
		
		
	}

	public TestEntity(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	

}
