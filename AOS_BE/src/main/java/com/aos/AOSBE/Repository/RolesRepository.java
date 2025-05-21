package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
    // Add custom query methods here if needed
}
