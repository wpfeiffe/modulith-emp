package com.wspfeiffer.modulithemp.league;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.wspfeiffer.modulithemp.league.internal.Team}
 */
public record TeamDto(Long id, @NotNull @Size(min = 2, max = 20) String teamCode,
                      @NotNull @Size(min = 3, max = 30) String teamName, LeagueDto league) implements Serializable {
}
