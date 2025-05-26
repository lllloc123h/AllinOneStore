package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    // Add custom query methods here if needed
}
