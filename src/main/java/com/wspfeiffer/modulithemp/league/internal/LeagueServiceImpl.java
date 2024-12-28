package com.wspfeiffer.modulithemp.league.internal;

import com.wspfeiffer.modulithemp.league.LeagueDto;
import com.wspfeiffer.modulithemp.league.LeagueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepository leagueRepository; // Assuming a repository exists.
    private final LeagueMapper leagueMapper;         // Mapper for entity-to-DTO conversions.

    @Override
    public List<LeagueDto> findAll() {
        return leagueMapper.toDto(leagueRepository.findAll());
    }

    @Override
    public Page<LeagueDto> findAll(Pageable pageable) {
        return leagueRepository.findAll(pageable).map(leagueMapper::toDto);
    }

    @Override
    public Optional<LeagueDto> findById(Long id) {
        return leagueRepository.findById(id)
                .map(leagueMapper::toDto); // Map to DTO if found.
    }

    @Override
    public LeagueDto save(LeagueDto leagueDto) {
        League league = leagueMapper.toEntity(leagueDto); // Convert DTO to entity.
        League savedLeague = leagueRepository.save(league); // Save entity.
        return leagueMapper.toDto(savedLeague);              // Convert saved entity back to DTO.
    }

    @Override
    public Optional<LeagueDto> deleteById(Long id) {
        Optional<League> leagueOptional = leagueRepository.findById(id);
        leagueOptional.ifPresent(leagueRepository::delete); // Delete if present.
        return leagueOptional.map(leagueMapper::toDto);     // Return the deleted entity as DTO.
    }
}

