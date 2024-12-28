package com.wspfeiffer.modulithemp.league;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    List<PlayerDto> findAll();
    Page<PlayerDto> findAll(Pageable pageable);
    Page<PlayerDto> findByFullName(String fullName, Pageable pageable);
    Page<PlayerDto> findByPosition(String position, Pageable pageable);
    Optional<PlayerDto> findById(Long id);
    PlayerDto save(PlayerDto companyDto);
    Optional<PlayerDto> deleteById(Long id);
}
