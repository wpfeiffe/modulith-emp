package com.wspfeiffer.modulithemp.company;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.wspfeiffer.modulithemp.company.internal.Employee}
 */
public record EmployeeDto(Long id, @NotNull @Size(min = 2, max = 40) String firstName,
                          @NotNull @Size(min = 2, max = 40) String lastName, @NotNull Boolean active,
                          @NotNull LocalDate startDate, @NotNull @Size(min = 8, max = 40) String title,
                          @NotNull DepartmentDto department) implements Serializable {
}
