package com.wspfeiffer.modulithemp.league.internal;

import com.wspfeiffer.modulithemp.league.PlayerDto;
import com.wspfeiffer.modulithemp.league.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository; // Assuming a repository exists.
    private final PlayerMapper playerMapper;         // Mapper for entity-to-DTO conversions.

    @Override
    public List<PlayerDto> findAll() {
        return playerRepository.findAll().stream()
                .map(playerMapper::toDto) // Map each entity to DTO.
                .collect(Collectors.toList());
    }

    @Override
    public Page<PlayerDto> findAll(Pageable pageable) {
        return playerRepository.findAll(pageable).map(playerMapper::toDto);
    }

    @Override
    public Optional<PlayerDto> findById(Long id) {
        return playerRepository.findById(id)
                .map(playerMapper::toDto); // Map to DTO if found.
    }

    @Override
    public Page<PlayerDto> findByFullName(String fullName, Pageable pageable) {
        return playerRepository.findByFullName(fullName, pageable).map(playerMapper::toDto);
    }

    @Override
    public Page<PlayerDto> findByPosition(String position, Pageable pageable) {
        return playerRepository.findByPosition(position, pageable).map(playerMapper::toDto);
    }


    @Override
    public PlayerDto save(PlayerDto playerDto) {
        Player player = playerMapper.toEntity(playerDto); // Convert DTO to entity.
        Player savedPlayer = playerRepository.save(player); // Save entity.
        return playerMapper.toDto(savedPlayer);              // Convert saved entity back to DTO.
    }

    @Override
    public Optional<PlayerDto> deleteById(Long id) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        playerOptional.ifPresent(playerRepository::delete); // Delete if present.
        return playerOptional.map(playerMapper::toDto);     // Return the deleted entity as DTO.
    }
}

