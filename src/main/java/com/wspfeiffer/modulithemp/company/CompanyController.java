package com.wspfeiffer.modulithemp.company;

import io.micrometer.core.annotation.Timed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import java.util.List;

@RestController
@RequestMapping("/api/companies")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed(value = "companies.get.all", description = "Get all companies")
    @Secured("ROLE_USER")
    public List<CompanyDto> all() {
        return companyService.findAll();
    }

    @GetMapping(value = {"/pageable"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed(value = "companies.get.pageable", description = "Get pageable list of companies")
    @Secured("ROLE_USER")
    public Page<CompanyDto> all(@SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable p) {
        return (Page<CompanyDto>) companyService.findAll(p);
    }


    @GetMapping("/{id}")
    @Timed(value = "companies.get.single", description = "Get company by id")
    @Secured("ROLE_USER")
    public ResponseEntity<CompanyDto> getById(@PathVariable Long id) {
        CompanyDto found = companyService.findById(id).orElse(null);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }

    @PutMapping("/{id}")
    @Timed(value = "companies.update", description = "Update company")
    @Transactional(value = Transactional.TxType.REQUIRED)
    @Secured("ROLE_USER")
    public ResponseEntity<CompanyDto> update(@PathVariable Long id, @RequestBody @Valid CompanyDto company) {
        if (company.id() == null || !company.id().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        CompanyDto origCompany = companyService.findById(id).orElse(null);
        if (origCompany == null) {
            return ResponseEntity.notFound().build();
        }
        CompanyDto updatedCompany = new CompanyDto(origCompany.id(), company.companyName());
        updatedCompany = companyService.save(updatedCompany);
        return ResponseEntity
                .ok(updatedCompany);
    }

    @PostMapping("")
    @Timed(value = "companies.create", description = "Create company")
    @Secured("ROLE_USER")
    public ResponseEntity<CompanyDto> create(@RequestBody @Valid CompanyDto company) {
        assert company.id() == null;
        final CompanyDto updated = companyService.save(company);
        return ResponseEntity
                .ok(updated);
    }

    @DeleteMapping("/{id}")
    @Timed(value = "companies.delete", description = "Delete company")
    @Transactional(value = Transactional.TxType.REQUIRED)
    @Secured("ROLE_USER")
    public ResponseEntity<CompanyDto> delete(@PathVariable Long id) {
        CompanyDto found = companyService.findById(id).orElse(null);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        companyService.deleteById(found.id());
        return ResponseEntity.ok(found);
    }

    private URI toUri(CompanyDto company) {
        return URI.create("/company/" + company.id());
    }
}
