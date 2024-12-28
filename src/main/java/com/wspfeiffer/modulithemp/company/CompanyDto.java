package com.wspfeiffer.modulithemp.company;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.wspfeiffer.modulithemp.company.internal.Company}
 */
public record CompanyDto(Long id, @NotNull @Size(min = 5, max = 60) String companyName) implements Serializable {
}
