package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.News;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer>, JpaSpecificationExecutor<News> {
    @Query("SELECT n FROM News n WHERE n.isHome = true ORDER BY n.createdAt DESC")
    List<News> findTopByHomeTrueOrderByCreatedAtDesc();
	// Add custom query methods here if needed
}