package com.wspfeiffer.modulithemp.company.internal;

import com.wspfeiffer.modulithemp.company.EmployeeDto;
import com.wspfeiffer.modulithemp.company.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository; // Repository for database operations.
    private final EmployeeMapper employeeMapper;         // Mapper for DTO-entity conversions.

    @Override
    public List<EmployeeDto> findAll() {
        return employeeMapper.toDto(employeeRepository.findAll());
    }

    @Override
    public Page<EmployeeDto> findAll(Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return employeePage.map(employeeMapper::toDto);
    }

    @Override
    public Optional<EmployeeDto> findById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDto); // Map to DTO if the entity exists.
    }

    @Override
    public Page<EmployeeDto> findByFirstName(String firstName, Pageable pageable) {
        return employeeRepository.findByFirstName(firstName, pageable).map(employeeMapper::toDto);
    }

    @Override
    public Page<EmployeeDto> findByActive(boolean active, Pageable pageable) {
        return employeeRepository.findByActive(active, pageable).map(employeeMapper::toDto);
    }

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        var employee = employeeMapper.toEntity(employeeDto);    // Convert DTO to entity.
        var savedEmployee = employeeRepository.save(employee);  // Persist the entity.
        return employeeMapper.toDto(savedEmployee);             // Map saved entity back to DTO.
    }

    @Override
    public Optional<EmployeeDto> deleteById(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        employeeOptional.ifPresent(employeeRepository::delete); // Delete the entity if it exists.
        return employeeOptional.map(employeeMapper::toDto);     // Return the deleted entity as a DTO.
    }
}
