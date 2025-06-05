package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
    // Add custom query methods here if needed
    // tìm role theo name
    Optional<Roles> findByName(String name);
}
