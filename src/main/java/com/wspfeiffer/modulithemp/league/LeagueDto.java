package com.wspfeiffer.modulithemp.league;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.wspfeiffer.modulithemp.league.internal.League}
 */
public record LeagueDto(Long id, @NotNull @Size(min = 5, max = 60) String leagueName) implements Serializable {
}
