package com.hdekker.calendarhome.database.tokencache;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenCacheRepository extends JpaRepository<TokenCacheEntity, Integer>{

}
