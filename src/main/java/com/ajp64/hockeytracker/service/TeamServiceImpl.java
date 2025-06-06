package com.ajp64.hockeytracker.service;

import com.ajp64.hockeytracker.aspects.LogExecution;
import com.ajp64.hockeytracker.exceptions.EntityNotFoundException;
import com.ajp64.hockeytracker.mapper.TeamMapper;
import com.ajp64.hockeytracker.model.PlayerEntity;
import com.ajp64.hockeytracker.model.TeamEntity;
import com.ajp64.hockeytracker.repository.PlayerRepository;
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
    private final PlayerRepository playerRepository;
    private final TeamMapper teamMapper;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository,
                           PlayerRepository playerRepository,
                           TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.teamMapper = teamMapper;
    }

    @Override
    @LogExecution
    public Team createTeam(Team newTeam)
    {
        if (newTeam.getTeamName() == null) {
            throw new NoNameException("No team name provided");
        }

        TeamEntity teamEntity = teamMapper.domainToEntity(newTeam);
        teamEntity.setPlayers(getPlayersForTeam(newTeam));

        TeamEntity savedVal = this.teamRepository.save(teamEntity);

        return teamMapper.entityToDomain(savedVal);
    }

    @Override
    public Team getTeam(String teamId)
    {
        return teamMapper.entityToDomain(teamRepository.findByPublicId(teamId));
    }

    @Override
    public Set<Team> getTeams() {

        return this.teamRepository.findAll()
                .stream().map(teamMapper::entityToDomain)
                .collect(Collectors.toSet());
    }

    private Set<PlayerEntity> getPlayersForTeam(Team team) {
        return team.getPlayers().stream()
                .map(player -> {
                    PlayerEntity playerEntity = playerRepository.findByPublicId(player.getPublicId());
                    if (playerEntity == null) {
                        throw new EntityNotFoundException("Player not found with id: " + player.getPublicId());
                    }
                    return playerEntity;
                })
                .collect(Collectors.toSet());
    }
}
