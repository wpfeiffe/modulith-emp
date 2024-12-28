package com.wspfeiffer.modulithemp.company.internal;

import com.wspfeiffer.modulithemp.company.DepartmentDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {CompanyMapper.class})
public interface DepartmentMapper {

    Department toEntity(DepartmentDto departmentDto);
    DepartmentDto toDto(Department department);
    List<Department> toEntity(List<DepartmentDto> departmentDto);
    List<DepartmentDto> toDto(List<Department> department);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Department partialUpdate(DepartmentDto departmentDto, @MappingTarget Department department);
}
