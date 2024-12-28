package com.wspfeiffer.modulithemp.company;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    List<DepartmentDto> findAll();

    Page<DepartmentDto> findAll(Pageable pageable);

    Optional<DepartmentDto> findById(Long id);

    DepartmentDto save(DepartmentDto department);

    void deleteById(Long id);
}
