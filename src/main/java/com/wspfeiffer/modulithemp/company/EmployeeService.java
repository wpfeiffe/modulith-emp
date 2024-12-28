package com.wspfeiffer.modulithemp.company;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<EmployeeDto> findAll();
    Page<EmployeeDto> findAll(Pageable pageable);
    Optional<EmployeeDto> findById(Long id);
    Page<EmployeeDto> findByFirstName(String firstName, Pageable pageable);
    Page<EmployeeDto> findByActive(boolean active, Pageable pageable);
    EmployeeDto save(EmployeeDto companyDto);
    Optional<EmployeeDto> deleteById(Long id);
}
