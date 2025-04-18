package com.ajp64.hockeytracker.service;

import com.ajp64.hockeytracker.aspects.LogExecution;
import com.ajp64.hockeytracker.converter.TeamEntityConverter;
import com.ajp64.hockeytracker.model.TeamEntity;
import com.ajp64.hockeytracker.repository.TeamRepository;
import com.ajp64.hockeytracker.exceptions.NoNameException;
import com.rest.server.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamEntityConverter converter;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, TeamEntityConverter converter) {
        this.teamRepository = teamRepository;
        this.converter = converter;
    }

    @Override
    @LogExecution
    public void createTeam(TeamEntity newTeam)
    {
        System.out.println("In Service");
        if (newTeam.getTeamName() != null) {
            this.teamRepository.save(newTeam);
        }
        else {
            throw new NoNameException("No team name provided");
        }
    }

    @Override
    public TeamEntity getTeam(String teamId)
    {
        return this.teamRepository.findByPublicId(teamId);
    }

    @Override
    public Set<Team> getTeams() {

        return this.teamRepository.findAll()
                .stream().map(converter::convert)
                .collect(Collectors.toSet());
    }
}
