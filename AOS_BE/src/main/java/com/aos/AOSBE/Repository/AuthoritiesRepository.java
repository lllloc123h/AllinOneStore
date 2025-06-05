package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer> {
    // Add custom query methods here if needed
    @Query("SELECT a FROM Authorities a WHERE a.accounts.email = :email")
    List<Authorities> findAllByEmail(String email);
}
