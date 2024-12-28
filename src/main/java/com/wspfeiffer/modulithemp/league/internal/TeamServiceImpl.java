package com.wspfeiffer.modulithemp.league.internal;

import com.wspfeiffer.modulithemp.league.TeamDto;
import com.wspfeiffer.modulithemp.league.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository; // Assuming a repository exists.
    private final TeamMapper teamMapper;         // Mapper for entity-to-DTO conversions.

    @Override
    public List<TeamDto> findAll() {
        return teamMapper.toDto(teamRepository.findAll());
    }

    public Page<TeamDto> findAll(Pageable pageable) {
        return teamRepository.findAll(pageable).map(teamMapper::toDto);
    }

    public Page<TeamDto> findByTeamCode(String teamCode, Pageable pageable) {
        return teamRepository.findByTeamCode(teamCode, pageable).map(teamMapper::toDto);
    }

    @Override
    public Optional<TeamDto> findById(Long id) {
        return teamRepository.findById(id)
                .map(teamMapper::toDto); // Map to DTO if found.
    }

    @Override
    public TeamDto save(TeamDto teamDto) {
        Team team = teamMapper.toEntity(teamDto); // Convert DTO to entity.
        Team savedTeam = teamRepository.save(team); // Save entity.
        return teamMapper.toDto(savedTeam);              // Convert saved entity back to DTO.
    }

    @Override
    public Optional<TeamDto> deleteById(Long id) {
        Optional<Team> teamOptional = teamRepository.findById(id);
        teamOptional.ifPresent(teamRepository::delete); // Delete if present.
        return teamOptional.map(teamMapper::toDto);     // Return the deleted entity as DTO.
    }
}

