package com.wspfeiffer.modulithemp.league;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<TeamDto> findAll();
    Page<TeamDto> findAll(Pageable pageable);
    Page<TeamDto> findByTeamCode(String teamCode, Pageable pageable);
    Optional<TeamDto> findById(Long id);
    TeamDto save(TeamDto companyDto);
    Optional<TeamDto> deleteById(Long id);
}
