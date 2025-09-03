package com.ajp64.hockeytracker.controller;

import com.ajp64.hockeytracker.exceptions.EntityNotFoundException;
import com.ajp64.hockeytracker.service.LeagueService;
import com.rest.server.model.League;
import com.rest.server.model.LeagueListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@RestController
@RequestMapping("/api/v1")
@Validated
public class LeagueController {
    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping("/leagues")
    public ResponseEntity<LeagueListResponse> getLeagues() {
        Set<League> leagues = this.leagueService.getLeagues();

        return ResponseEntity.ok(new LeagueListResponse(leagues));
    }

    @GetMapping("/leagues/{leagueId}")
    public ResponseEntity<League> getLeagueById(@PathVariable @NotBlank String leagueId) {
        try {
            League league = this.leagueService.getLeague(leagueId);
            return ResponseEntity.ok(league);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/leagues")
    public ResponseEntity<League> createLeague(@RequestBody @Valid League league) {
        League createdLeague = this.leagueService.createLeague(league);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdLeague);
    }

    @PutMapping("/leagues/{guid}")
    public ResponseEntity<League> updateLeague(@PathVariable @NotBlank String guid, 
                                            @RequestBody @Valid League league) {
        try {
            League updatedLeague = leagueService.updateLeague(guid, league);
            return ResponseEntity.ok(updatedLeague);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/leagues/{guid}")
    public ResponseEntity<Void> deleteLeague(@PathVariable @NotBlank String guid) {
        try {
            leagueService.deleteLeague(guid);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
