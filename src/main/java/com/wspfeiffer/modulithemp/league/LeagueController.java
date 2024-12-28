package com.wspfeiffer.modulithemp.league;


import io.micrometer.core.annotation.Timed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/leagues")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class LeagueController {
    
    private final LeagueService leagueService;
    
    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed(value = "leagues_get_all", description = "Get all leagues")
    @Secured("ROLE_USER")
    public List<LeagueDto> all() {
        return leagueService.findAll();
    }

    @GetMapping(value = {"/pageable"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed(value = "leagues_get_pageable", description = "Get pageable list of leagues")
    @Secured("ROLE_USER")
    public Page<LeagueDto> all(@SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable p) {
        return leagueService.findAll(p);
    }

    @GetMapping("/{id}")
    @Timed(value = "leagues.get.single", description = "Get league by id")
    @Secured("ROLE_USER")
    public ResponseEntity<LeagueDto> getById(@PathVariable Long id) {
        LeagueDto found = leagueService.findById(id).orElse(null);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }

    @PutMapping("/{id}")
    @Timed(value = "leagues.update", description = "Update league")
    @Transactional(value = Transactional.TxType.REQUIRED)
    @Secured("ROLE_USER")
    public ResponseEntity<LeagueDto> update(@PathVariable Long id, @RequestBody @Valid LeagueDto league) {
        if (league.id() == null || !league.id().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        LeagueDto origLeague = leagueService.findById(id).orElse(null);
        if (origLeague == null) {
            return ResponseEntity.notFound().build();
        }
        LeagueDto updatedLeague = new LeagueDto(origLeague.id(), league.leagueName());
        updatedLeague = leagueService.save(updatedLeague);
        return ResponseEntity
                .ok(updatedLeague);
    }

    @PostMapping("")
    @Timed(value = "leagues.create", description = "Create league")
    @Secured("ROLE_USER")
    public ResponseEntity<LeagueDto> create(@RequestBody @Valid LeagueDto league) {
        assert league.id() == null;
        final LeagueDto updated = leagueService.save(league);
        return ResponseEntity
                .ok(updated);
    }

    @DeleteMapping("/{id}")
    @Timed(value = "leagues.delete", description = "Delete league")
    @Transactional(value = Transactional.TxType.REQUIRED)
    @Secured("ROLE_USER")
    public ResponseEntity<LeagueDto> delete(@PathVariable Long id) {
        LeagueDto found = leagueService.findById(id).orElse(null);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        leagueService.deleteById(found.id());
        return ResponseEntity.ok(found);
    }

    private URI toUri(LeagueDto league) {
        return URI.create("/league/" + league.id());
    }
}
