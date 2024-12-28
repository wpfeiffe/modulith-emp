package com.wspfeiffer.modulithemp.company;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.wspfeiffer.modulithemp.company.internal.Department}
 */
public record DepartmentDto(Long id, @Size(min = 2, max = 20) String deptCode, @Size(min = 3, max = 30) String deptName,
                            @NotNull CompanyDto company) implements Serializable {
}
