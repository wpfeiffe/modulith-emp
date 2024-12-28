package com.wspfeiffer.modulithemp.company;

import com.wspfeiffer.modulithemp.company.internal.Department;
import com.wspfeiffer.modulithemp.company.internal.DepartmentRepository;
import io.micrometer.core.annotation.Timed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

@RestController()
@RequestMapping("/api/departments")
@PreAuthorize("isAuthenticated()")
public class DepartmentController {
    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed(value = "departments.get.all", description = "Get all departments")
    @Secured("ROLE_USER")
    public List<Department> all() {
        return (ArrayList<Department>) departmentRepository.findAll();
    }

    @GetMapping(value = {"/pageable"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed(value = "departments.get.pageable", description = "Get pageable list of departments")
    @Secured("ROLE_USER")
    public Page<Department> all(@SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable p) {
        return (Page<Department>) departmentRepository.findAll(p);
    }

    @GetMapping("/{id}")
    @Timed(value = "departments.get.single", description = "Get department by id")
    @Secured("ROLE_USER")
    public ResponseEntity<Department> getById(@PathVariable Long id) {
        Department found = departmentRepository.findById(id).orElse(null);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }

    @GetMapping("/findBy")
    @Timed(value = "departments.findby", description = "Find department by dept code")
    @Secured("ROLE_USER")
    public ResponseEntity<Page<Department>> getFindBy(Pageable p, @RequestParam String deptCode) {
        if (deptCode == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(departmentRepository.findByDeptCode(deptCode, p));
        }
    }


    @PutMapping("/{id}")
    @Timed(value = "departments.update", description = "Update department")
    @Transactional(value = Transactional.TxType.REQUIRED)
    @Secured("ROLE_USER")
    public ResponseEntity<Department> update(Long id, @RequestBody @Valid Department department) {
        if (department.getId() == null || !department.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Department origDepartment = departmentRepository.findById(id).orElse(null);
        if (origDepartment == null) {
            return ResponseEntity.notFound().build();
        }
        origDepartment.setDeptCode(department.getDeptCode());
        origDepartment.setDeptName(department.getDeptName());
        origDepartment = departmentRepository.save(origDepartment);
        return ResponseEntity
                .ok(origDepartment);
    }

    @PostMapping("")
    @Timed(value = "departments.create", description = "Create department")
    @Secured("ROLE_USER")
    public ResponseEntity<Department> create(@RequestBody @Valid Department department) {
        assert department.getId() == null;
        final Department updated = departmentRepository.save(department);
        return ResponseEntity
                .ok(updated);
    }

    @DeleteMapping("/{id}")
    @Timed(value = "departments.delete", description = "Delete department")
    @Transactional(value = Transactional.TxType.REQUIRED)
    @Secured("ROLE_USER")
    public ResponseEntity<Department> delete(Long id) {
        Department found = departmentRepository.findById(id).orElse(null);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        departmentRepository.delete(found);
        return ResponseEntity.ok(found);
    }

    private URI toUri(Department department) {
        return URI.create("/department/" + department.getId());
    }
}
