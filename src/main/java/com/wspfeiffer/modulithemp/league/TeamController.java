package com.wspfeiffer.modulithemp.league;

import io.micrometer.core.annotation.Timed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
@RequestMapping("/api/teams")
@PreAuthorize("isAuthenticated()")
public class TeamController
{
    private final TeamService teamService;

    public TeamController(TeamService teamService)
    {
        this.teamService = teamService;
    }

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed(value = "teams.get.all", description = "Get all teams")
    @Secured("ROLE_USER")
    public List<TeamDto> all() {
        return (ArrayList<TeamDto>) teamService.findAll();
    }

    @GetMapping(value = {"/pageable"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed(value = "teams.get.pageable", description = "Get pageable list of teams")
    @Secured("ROLE_USER")
    public Page<TeamDto> all(@SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable p) {
        return (Page<TeamDto>) teamService.findAll(p);
    }

    @GetMapping("/{id}")
    @Timed(value = "teams.get.single", description = "Get teamDto by id")
    @Secured("ROLE_USER")
    public ResponseEntity<TeamDto> getById(@PathVariable Long id) {
        TeamDto found = teamService.findById(id).orElse(null);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }

    @GetMapping("/findBy")
    @Timed(value = "teams.findby", description = "Find teamDto by name active")
    @Secured("ROLE_USER")
    public ResponseEntity<Page<TeamDto>> getFindBy(@RequestParam String teamCode, Pageable p) {
        if (teamCode == null) {
            return ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.ok(teamService.findByTeamCode(teamCode, p));
        }
    }



    @PutMapping("/{id}")
    @Timed(value = "teams.update", description = "Update teamDto")
    @Transactional(value = Transactional.TxType.REQUIRED)
    @Secured("ROLE_USER")
    public ResponseEntity<TeamDto> update(@PathVariable Long id, @RequestBody @Valid TeamDto teamDto) {
         if (teamDto.id() == null || !teamDto.id().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        TeamDto origTeam = teamService.findById(id).orElse(null);
        if (origTeam == null) {
            return ResponseEntity.notFound().build();
        }
        TeamDto updated = new TeamDto(origTeam.id(), teamDto.teamCode(), teamDto.teamName(), origTeam.league());
        updated = teamService.save(updated);
        return ResponseEntity
            .ok(updated);
    }

    @PostMapping("")
    @Timed(value = "teams.create", description = "Create teamDto")
    @Secured("ROLE_USER")
    public ResponseEntity<TeamDto> create(@RequestBody @Valid TeamDto teamDto) {
        assert teamDto.id() == null;
        final TeamDto updated = teamService.save(teamDto);
        return ResponseEntity
            .ok(updated);
    }

    @DeleteMapping("/{id}")
    @Timed(value = "teams.delete", description = "Delete teamDto")
    @Transactional(value = Transactional.TxType.REQUIRED)
    @Secured("ROLE_USER")
    public ResponseEntity<TeamDto> delete(@PathVariable Long id) {
        TeamDto found = teamService.findById(id).orElse(null);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        teamService.deleteById(found.id());
        return ResponseEntity.ok(found);
    }

    private URI toUri(TeamDto teamDto) {
        return URI.create("/teamDto/" + teamDto.id());
    }
}
