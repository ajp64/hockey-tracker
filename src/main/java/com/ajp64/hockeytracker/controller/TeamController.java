package com.ajp64.hockeytracker.controller;

import com.ajp64.hockeytracker.model.PlayerEntity;
import com.ajp64.hockeytracker.model.TeamEntity;
import com.ajp64.hockeytracker.service.PlayerService;
import com.ajp64.hockeytracker.service.TeamService;
import com.rest.server.model.PlayerListResponse;
import com.rest.server.model.TeamListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("teams")
    public ResponseEntity<TeamListResponse> listTeams(){
        var teams = teamService.getTeams();

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new TeamListResponse(teams));
    }

    @GetMapping("teams/{id}")
    public ResponseEntity<TeamEntity> getTeamById(@PathVariable String id){
        var team = teamService.getTeam(id);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(team);
    }

    @PostMapping("teams")
    public ResponseEntity<TeamEntity> createTeam(@RequestBody TeamEntity team){
        System.out.println("In controller");

        teamService.createTeam(team);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(team);
    }
}
