package com.ajp64.hockeytracker.service;

import com.ajp64.hockeytracker.aspects.LogExecution;
import com.ajp64.hockeytracker.exceptions.EntityNotFoundException;
import com.ajp64.hockeytracker.mapper.PlayerMapper;
import com.ajp64.hockeytracker.model.LeagueEntity;
import com.ajp64.hockeytracker.model.TeamEntity;
import com.ajp64.hockeytracker.repository.LeagueRepository;
import com.ajp64.hockeytracker.repository.TeamRepository;
import com.rest.server.model.LeagueData;
import com.rest.server.model.Player;
import com.ajp64.hockeytracker.exceptions.NoNameException;
import com.ajp64.hockeytracker.model.PlayerEntity;
import com.rest.server.model.TeamData;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ajp64.hockeytracker.repository.PlayerRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;
    private final PlayerMapper playerMapper;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository,
                             TeamRepository teamRepository,
                             LeagueRepository leagueRepository,
                             PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
        this.playerMapper = playerMapper;
    }

    @Override
    @LogExecution
    public Player createPlayer(Player newPlayer)
    {
        if (newPlayer.getPlayerName() == null) {
            throw new NoNameException("No player name provided");
        }

        PlayerEntity playerEntity = playerMapper.domainToEntity(newPlayer);

        playerEntity.setTeams(getTeamsForPlayer(newPlayer));
        playerEntity.setLeagues(getLeaguesForPlayer(newPlayer));

        PlayerEntity savedVal = this.playerRepository.save(playerEntity);

        return playerMapper.entityToDomain(savedVal);
    }

    @Override
    public Player getPlayer(String playerId) {
        return playerMapper.entityToDomain(this.playerRepository.findByPublicId(playerId));
    }

    @Override
    public Set<Player> getPlayers() {

        return this.playerRepository.findAll()
                .stream().map(playerMapper::entityToDomain)
                .collect(Collectors.toSet());
    }

    private Set<TeamEntity> getTeamsForPlayer(Player player) {
        final Set<String> teamIds = player.getTeams().stream()
                .map(TeamData::getPublicId).collect(Collectors.toSet());

        Set<TeamEntity> retVal =  teamRepository.findAllByPublicIdIn(teamIds);

        if (retVal.size() != teamIds.size()) {
            throw new EntityNotFoundException("Some Teams were not found.");
        }

        return retVal;
    }

    private Set<LeagueEntity> getLeaguesForPlayer(Player player) {
        final Set<String> leagueIds = player.getLeagues().stream()
                .map(LeagueData::getPublicId).collect(Collectors.toSet());

        Set<LeagueEntity> retVal = leagueRepository.findAllByPublicIdIn(leagueIds);

        if (retVal.size() != leagueIds.size()) {
            throw new EntityNotFoundException("Some Teams were not found.");
        }

        return retVal;
    }
}
