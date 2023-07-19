package com.hdekker.calendarhome;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestSchema extends JpaRepository<TestEntity, Long> {

}
