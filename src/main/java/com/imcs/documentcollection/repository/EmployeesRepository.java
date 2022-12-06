package com.imcs.documentcollection.repository;

import com.imcs.documentcollection.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeesRepository extends JpaRepository<Employee, String> {
	Optional<Employee> findById(int id);
	Optional<Employee> findByUid(long id);
}
