package com.ajp64.hockeytracker.service;

import com.ajp64.hockeytracker.model.TeamEntity;
import com.rest.server.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TeamService{

    void createTeam(TeamEntity newTeam);

    TeamEntity getTeam(String teamId);

    Set<Team> getTeams();
}

