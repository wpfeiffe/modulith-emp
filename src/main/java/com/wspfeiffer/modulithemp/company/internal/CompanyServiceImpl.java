package com.wspfeiffer.modulithemp.company.internal;

import com.wspfeiffer.modulithemp.company.CompanyDto;
import com.wspfeiffer.modulithemp.company.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository; // Assuming a repository exists.
    private final CompanyMapper companyMapper;         // Mapper for entity-to-DTO conversions.

    @Override
    public List<CompanyDto> findAll() {
        return companyRepository.findAll().stream()
                .map(companyMapper::toDto) // Map each entity to DTO.
                .collect(Collectors.toList());
    }

    @Override
    public Page<CompanyDto> findAll(Pageable pageable) {
        // Proxies the call to the repository's findAll method with pagination
        return companyRepository.findAll(pageable).map(companyMapper::toDto);
    }

    @Override
    public Optional<CompanyDto> findById(Long id) {
        return companyRepository.findById(id)
                .map(companyMapper::toDto); // Map to DTO if found.
    }

    @Override
    public CompanyDto save(CompanyDto companyDto) {
        Company company = companyMapper.toEntity(companyDto); // Convert DTO to entity.
        Company savedCompany = companyRepository.save(company); // Save entity.
        return companyMapper.toDto(savedCompany);              // Convert saved entity back to DTO.
    }

    @Override
    public Optional<CompanyDto> deleteById(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        companyOptional.ifPresent(companyRepository::delete); // Delete if present.
        return companyOptional.map(companyMapper::toDto);     // Return the deleted entity as DTO.
    }
}
