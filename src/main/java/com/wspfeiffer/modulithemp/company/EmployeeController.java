package com.wspfeiffer.modulithemp.company;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;
    private final EmployeeUpdateNotifier employeeUpdateNotifier;
    private final MeterRegistry meterRegistry;

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed("employees.get.all")
    @Secured("ROLE_USER")
    public List<EmployeeDto> all() {
        return (ArrayList<EmployeeDto>) employeeService.findAll();
    }

    @GetMapping(value = {"/pageable"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed("employees.get.pageable")
    @Secured("ROLE_USER")
    public Page<EmployeeDto> all(@SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable p) {

        return (Page<EmployeeDto>) employeeService.findAll(p);
    }

    @GetMapping("/{id}")
    @Timed("employees.get.single")
    @Secured("ROLE_USER")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
        EmployeeDto found = employeeService.findById(id).orElse(null);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }

    @GetMapping("/findBy")
    @Timed("employees.findby")
    @Secured("ROLE_USER")
    public ResponseEntity<Page<EmployeeDto>> getFindBy(Pageable p, @RequestParam Optional<String> firstName, @RequestParam Optional<Boolean> active) {
        if ((firstName.isEmpty() && active.isEmpty()) || (firstName.isPresent() && active.isPresent())) {
            return ResponseEntity.badRequest().build();
        } else if (firstName.isPresent()) {
            Page<EmployeeDto> employees = employeeService.findByFirstName(firstName.get(), p);
            if (employees == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(employees);
        } else {
            Page<EmployeeDto> employees = employeeService.findByActive(active.get(), p);
            if (employees == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(employees);
        }
    }

    @PutMapping("/{id}")
    @Timed("employees.update")
    @Transactional(value = Transactional.TxType.REQUIRED)
    @Secured("ROLE_USER")
    public ResponseEntity<EmployeeDto> update(@PathVariable Long id, @RequestBody @Valid EmployeeDto employeeDto) {
        if (employeeDto.id() == null || !employeeDto.id().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        EmployeeDto origEmployee = employeeService.findById(id).orElse(null);
        if (origEmployee == null) {
            return ResponseEntity.notFound().build();
        }
        EmployeeDto updatedEmployee = new EmployeeDto(
                origEmployee.id(),
                employeeDto.firstName(),
                employeeDto.lastName(),
                employeeDto.active(),
                employeeDto.startDate(),
                employeeDto.title(),
                origEmployee.department());
        EmployeeDto savedEmployee = employeeService.save(updatedEmployee);
        employeeUpdateNotifier.notify(savedEmployee);
        return ResponseEntity
                .ok(savedEmployee);
    }

    @PostMapping("/")
    @Timed("employees.create")
    @Secured("ROLE_USER")
    public ResponseEntity<EmployeeDto> create(@RequestBody @Valid EmployeeDto EmployeeDto) {
        assert EmployeeDto.id() == null;
        final EmployeeDto updated = employeeService.save(EmployeeDto);
        return ResponseEntity
                .ok(updated);
    }

    @DeleteMapping("/{id}")
    @Timed(value = "employees.delete", description = "Delete EmployeeDto")
    @Transactional(value = Transactional.TxType.REQUIRED)
    @Secured("ROLE_USER")
    public ResponseEntity<EmployeeDto> delete(@PathVariable Long id) {
        EmployeeDto found = employeeService.findById(id).orElse(null);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        employeeService.deleteById(found.id());
        return ResponseEntity.ok(found);
    }

    private URI toUri(EmployeeDto EmployeeDto) {
        return URI.create("/api/employees/" + EmployeeDto.id());
    }
}
