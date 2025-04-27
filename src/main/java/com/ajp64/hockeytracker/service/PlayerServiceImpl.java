package com.ajp64.hockeytracker.service;

import com.ajp64.hockeytracker.aspects.LogExecution;
import com.ajp64.hockeytracker.converter.PlayerDomainToEntityConverter;
import com.ajp64.hockeytracker.converter.PlayerEntityToDomainConverter;
import com.ajp64.hockeytracker.converter.TeamEntityToDataConverter;
import com.ajp64.hockeytracker.exceptions.EntityNotFoundException;
import com.ajp64.hockeytracker.model.TeamEntity;
import com.ajp64.hockeytracker.repository.TeamRepository;
import com.rest.server.model.Player;
import com.ajp64.hockeytracker.exceptions.NoNameException;
import com.ajp64.hockeytracker.model.PlayerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ajp64.hockeytracker.repository.PlayerRepository;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final PlayerEntityToDomainConverter entityConverter;
    private final PlayerDomainToEntityConverter domainConverter;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository,
                             TeamRepository teamRepository,
                             PlayerEntityToDomainConverter entityConverter,
                             PlayerDomainToEntityConverter domainConverter) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.entityConverter = entityConverter;
        this.domainConverter = domainConverter;
    }

    @Override
    @LogExecution
    public Player createPlayer(Player newPlayer)
    {
        if (newPlayer.getPlayerName() == null) {
            throw new NoNameException("No player name provided");
        }

        PlayerEntity playerEntity = domainConverter.convert(newPlayer);
        playerEntity.setTeams(getTeamsForPlayer(newPlayer));

        PlayerEntity savedVal = this.playerRepository.save(playerEntity);

        return entityConverter.convert(savedVal);
    }

    @Override
    public Player getPlayer(String playerId) {
        return entityConverter.convert(this.playerRepository.findByPublicId(playerId));
    }

    @Override
    public Set<Player> getPlayers() {

        return this.playerRepository.findAll()
                .stream().map(entityConverter::convert)
                .collect(Collectors.toSet());
    }

    private Set<TeamEntity> getTeamsForPlayer(Player player) {
        return player.getTeams().stream()
                .map(team -> {
                    TeamEntity teamEntity = teamRepository.findByPublicId(team.getPublicId());
                    if (teamEntity == null) {
                        throw new EntityNotFoundException("Team not found with id: " + team.getPublicId());
                    }
                    return teamEntity;
                })
                .collect(Collectors.toSet());
    }
}
