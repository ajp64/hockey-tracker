package com.ajp64.hockeytracker.service;

import com.ajp64.hockeytracker.model.TeamEntity;
import com.rest.server.model.Team;

import java.util.Set;

public interface TeamService{

    Team createTeam(Team newTeam);

    Team getTeam(String teamId);

    Set<Team> getTeams();
}

