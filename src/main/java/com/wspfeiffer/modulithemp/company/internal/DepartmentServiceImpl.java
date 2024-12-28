package com.wspfeiffer.modulithemp.company.internal;

import com.wspfeiffer.modulithemp.company.DepartmentDto;
import com.wspfeiffer.modulithemp.company.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentDto> findAll() {
        // Proxies the call to the repository's findAll method
        return departmentMapper.toDto(departmentRepository.findAll());
    }

    @Override
    public Page<DepartmentDto> findAll(Pageable pageable) {
        // Proxies the call to the repository's findAll method with pagination
        return departmentRepository.findAll(pageable).map(departmentMapper::toDto);
    }

    @Override
    public Optional<DepartmentDto> findById(Long id) {
        // Proxies the call to the repository's findById method
        return departmentRepository.findById(id).map(departmentMapper::toDto);
    }

    @Override
    public DepartmentDto save(DepartmentDto department) {
        // Proxies the call to the repository's save method
        return departmentMapper.toDto(departmentRepository.save(departmentMapper.toEntity(department)));
    }

    @Override
    public void deleteById(Long id) {
        // Proxies the call to the repository's deleteById method
        departmentRepository.deleteById(id);
    }
}
