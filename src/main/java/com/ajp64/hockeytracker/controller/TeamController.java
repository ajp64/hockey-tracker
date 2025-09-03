package com.ajp64.hockeytracker.controller;

import com.ajp64.hockeytracker.exceptions.EntityNotFoundException;
import com.ajp64.hockeytracker.service.TeamService;
import com.rest.server.model.Team;
import com.rest.server.model.TeamListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/v1")
@Validated
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/teams")
    public ResponseEntity<TeamListResponse> listTeams(){
        var teams = teamService.getTeams();

        return ResponseEntity.ok(new TeamListResponse(teams));
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable @NotBlank String id){
        try {
            var team = teamService.getTeam(id);
            return ResponseEntity.ok(team);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/teams")
    public ResponseEntity<Team> createTeam(@RequestBody @Valid Team team){
        Team retval = teamService.createTeam(team);

        return ResponseEntity.status(HttpStatus.CREATED).body(retval);
    }

    @PutMapping("/teams/{guid}")
    public ResponseEntity<Team> updateTeam(@PathVariable @NotBlank String guid, 
                                        @RequestBody @Valid Team team) {
        try {
            Team updatedTeam = teamService.updateTeam(guid, team);
            return ResponseEntity.ok(updatedTeam);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/teams/{guid}")
    public ResponseEntity<Void> deleteTeam(@PathVariable @NotBlank String guid) {
        try {
            teamService.deleteTeam(guid);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
