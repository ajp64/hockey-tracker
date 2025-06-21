package com.ajp64.hockeytracker.controller;

import com.ajp64.hockeytracker.service.LeagueService;
import com.rest.server.model.League;
import com.rest.server.model.LeagueListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class LeagueController {
    LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping("leagues")
    public ResponseEntity<LeagueListResponse> getLeagues(String leagueId) {
        Set<League> leagues = this.leagueService.getLeagues();

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new LeagueListResponse(leagues));
    }

    @GetMapping("leagues/{id}")
    public ResponseEntity<League> getLeague(@PathVariable String leagueId) {
        League league = this.leagueService.getLeague(leagueId);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(league);
    }

    @PostMapping("leagues")
    public ResponseEntity<League> createLeague(@RequestBody League league) {
        League createdLeague = this.leagueService.createLeague(league);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(createdLeague);
    }
}
