package com.wspfeiffer.modulithemp.company;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    List<CompanyDto> findAll();
    Page<CompanyDto> findAll(Pageable pageable);
    Optional<CompanyDto> findById(Long id);
    CompanyDto save(CompanyDto companyDto);
    Optional<CompanyDto> deleteById(Long id);
}
