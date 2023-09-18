package com.hdekker.calendarhome.database.tokencache;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TokenCacheEntity {

	Integer id;
	String cache;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(length = 100000)
	public String getCache() {
		return cache;
	}
	public void setCache(String cache) {
		this.cache = cache;
	}
	
	
	
}
