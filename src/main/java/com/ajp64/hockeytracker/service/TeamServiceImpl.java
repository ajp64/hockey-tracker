package com.ajp64.hockeytracker.service;

import com.ajp64.hockeytracker.aspects.LogExecution;
import com.ajp64.hockeytracker.converter.TeamDomainToEntityConverter;
import com.ajp64.hockeytracker.converter.TeamEntityToDomainConverter;
import com.ajp64.hockeytracker.exceptions.EntityNotFoundException;
import com.ajp64.hockeytracker.model.PlayerEntity;
import com.ajp64.hockeytracker.model.TeamEntity;
import com.ajp64.hockeytracker.repository.PlayerRepository;
import com.ajp64.hockeytracker.repository.TeamRepository;
import com.ajp64.hockeytracker.exceptions.NoNameException;
import com.rest.server.model.Player;
import com.rest.server.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final TeamEntityToDomainConverter entityConverter;
    private final TeamDomainToEntityConverter domainConverter;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository,
                           PlayerRepository playerRepository,
                           TeamEntityToDomainConverter entityConverter,
                           TeamDomainToEntityConverter domainConverter) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.entityConverter = entityConverter;
        this.domainConverter = domainConverter;
    }

    @Override
    @LogExecution
    public Team createTeam(Team newTeam)
    {
        if (newTeam.getTeamName() == null) {
            throw new NoNameException("No team name provided");
        }

        TeamEntity teamEntity = domainConverter.convert(newTeam);
        teamEntity.setPlayers(getPlayersForTeam(newTeam));

        TeamEntity savedVal = this.teamRepository.save(teamEntity);

        return entityConverter.convert(savedVal);
    }

    @Override
    public Team getTeam(String teamId)
    {
        return entityConverter.convert(teamRepository.findByPublicId(teamId));
    }

    @Override
    public Set<Team> getTeams() {

        return this.teamRepository.findAll()
                .stream().map(entityConverter::convert)
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
