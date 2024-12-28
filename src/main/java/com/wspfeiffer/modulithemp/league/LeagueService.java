package com.wspfeiffer.modulithemp.league;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LeagueService {
    List<LeagueDto> findAll();
    Page<LeagueDto> findAll(Pageable pageable);
    Optional<LeagueDto> findById(Long id);
    LeagueDto save(LeagueDto companyDto);
    Optional<LeagueDto> deleteById(Long id);
}
