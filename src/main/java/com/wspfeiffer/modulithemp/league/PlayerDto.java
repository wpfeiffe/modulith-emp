package com.wspfeiffer.modulithemp.league;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.wspfeiffer.modulithemp.league.internal.Player}
 */
public record PlayerDto(Long id, @NotNull @Size(min = 2, max = 100) String fullName,
                        @NotNull @Size(min = 5, max = 40) String position, TeamDto team) implements Serializable {
}
