package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, String> {
    // Add custom query methods here if needed
}
