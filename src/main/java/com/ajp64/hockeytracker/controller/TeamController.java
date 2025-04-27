package com.ajp64.hockeytracker.controller;

import com.ajp64.hockeytracker.service.TeamService;
import com.rest.server.model.Team;
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
    public ResponseEntity<Team> getTeamById(@PathVariable String id){
        var team = teamService.getTeam(id);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(team);
    }

    @PostMapping("teams")
    public ResponseEntity<Team> createTeam(@RequestBody Team team){
        Team retval = teamService.createTeam(team);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(retval);
    }
}
