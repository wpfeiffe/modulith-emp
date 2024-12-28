package com.wspfeiffer.modulithemp.company.internal;

import com.wspfeiffer.modulithemp.company.EmployeeDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {DepartmentMapper.class})
public interface EmployeeMapper {
    Employee toEntity(EmployeeDto employeeDto);
    List<Employee> toEntity(List<EmployeeDto> employeeDtos);

    EmployeeDto toDto(Employee employee);
    List<EmployeeDto> toDto(List<Employee> employees);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Employee partialUpdate(EmployeeDto employeeDto, @MappingTarget Employee employee);
}
