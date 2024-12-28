package com.wspfeiffer.modulithemp.league;

import com.wspfeiffer.modulithemp.company.EmployeeDto;
import com.wspfeiffer.modulithemp.company.EmployeeService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/players")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class PlayerController
{
    private static final Logger LOG = LoggerFactory.getLogger(PlayerController.class);

    private final PlayerService playerService;
    private final EmployeeService employeeService;
    private final MeterRegistry meterRegistry;


    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed(value = "players.get.all", description = "Get all players")
    @Secured("ROLE_USER")
    public List<PlayerDto> all() {
        return playerService.findAll();
    }

    @GetMapping(value = {"/pageable"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed(value = "players.get.pageable", description = "Get pageable list of players")
    @Secured("ROLE_USER")
    public Page<PlayerDto> all(@SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable p) {
        return playerService.findAll(p);
    }

    @GetMapping("/{id}")
    @Timed(value = "players.get.single", description = "Get player by id")
    @Secured("ROLE_USER")
    public HttpEntity<PlayerDto> getById(@PathVariable Long id) {
        PlayerDto found = playerService.findById(id).orElse(null);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }

    @GetMapping("/{id}/extended")
    @Timed(value = "players.get.single.ext", description = "Get single player with employee info")
    @Secured("ROLE_USER")
    public ResponseEntity<EmployeePlayer> getExtendedById(@PathVariable Long id) {
        EmployeeDto employee = employeeService.findById(id).orElse(null);
        PlayerDto player = playerService.findById(id).orElse(null);
        if (player == null || employee == null ) {
            return ResponseEntity.notFound().build();
        }

        PlayerDto updatedPlayer = new PlayerDto(player.id(), player.fullName() + "Test1", player.position(), player.team());
        return ResponseEntity.ok(new EmployeePlayer(employee, updatedPlayer));
    }

    @GetMapping("/findBy")
    @Timed(value = "players.findby", description = "Find player by name active")
    @Secured("ROLE_USER")
    public ResponseEntity<Page<PlayerDto>> getFindBy(Pageable p, @RequestParam Optional<String> fullName, @RequestParam Optional<String> position) {
        if ((fullName.isEmpty() && position.isEmpty()) || (fullName.isPresent() && position.isPresent())) {
            return ResponseEntity.badRequest().build();
        }
        else if (fullName.isPresent()) {
           Page<PlayerDto> players = playerService.findByFullName(fullName.get(), p);
            if (players == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(players);
        }
        else {
            Page<PlayerDto> players = playerService.findByPosition(position.get(), p);
            if (players == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(players);
        }
    }

    @PutMapping("/{id}")
    @Timed(value = "players.update", description = "Update player")
    @Transactional(value = Transactional.TxType.REQUIRED)
    @Secured("ROLE_USER")
    public ResponseEntity<PlayerDto> update(@PathVariable Long id, @RequestBody @Valid PlayerDto player) {
        if (player.id() == null || !player.id().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        PlayerDto origPlayer = playerService.findById(id).orElse(null);
        if (origPlayer == null) {
            return ResponseEntity.notFound().build();
        }
        PlayerDto updated = new PlayerDto(origPlayer.id(), player.fullName(), player.position(), origPlayer.team());
        updated = playerService.save(updated);
        return ResponseEntity
            .ok(updated);
    }

    @PostMapping("")
    @Timed(value = "players.create", description = "Create player")
    @Secured("ROLE_USER")
    public ResponseEntity<PlayerDto> create(@RequestBody @Valid PlayerDto player) {
        assert player.id() == null;
        final PlayerDto updated = playerService.save(player);
        return ResponseEntity
            .ok(updated);
    }

    @DeleteMapping("/{id}")
    @Timed(value = "players.delete", description = "Delete player")
    @Transactional(value = Transactional.TxType.REQUIRED)
    @Secured("ROLE_USER")
    public ResponseEntity<PlayerDto> delete(@PathVariable Long id) {
        PlayerDto found = playerService.findById(id).orElse(null);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        playerService.deleteById(found.id());
        return ResponseEntity.ok(found);
    }

    private URI toUri(PlayerDto player) {
        return URI.create("/player/" + player.id());
    }
}
