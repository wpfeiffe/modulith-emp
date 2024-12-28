package com.wspfeiffer.modulithemp.company.internal;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>
{
    Page<Employee> findByFirstName(String firstName, Pageable p);
    Page<Employee> findByActive(Boolean active, Pageable p);
}
