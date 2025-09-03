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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ajp64.hockeytracker.repository.PlayerRepository;

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
    @Transactional(readOnly = true)
    public Player getPlayer(String playerId) {
        PlayerEntity playerEntity = this.playerRepository.findByPublicId(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found for guid: " + playerId));

        return playerMapper.entityToDomain(playerEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Player> getPlayers() {

        return this.playerRepository.findAll()
                .stream().map(playerMapper::entityToDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Player updatePlayer(String guid, Player update) {
        PlayerEntity playerToUpdate = this.playerRepository.findByPublicId(guid)
                .orElseThrow(() -> new EntityNotFoundException("Player not found for guid: " + guid));

        BeanUtils.copyProperties(update, playerToUpdate, "id", "publicId", "leagues", "teams");
        playerToUpdate.setLeagues(getLeaguesForPlayer(update));
        playerToUpdate.setTeams(getTeamsForPlayer(update));

        return playerMapper.entityToDomain(playerRepository.save(playerToUpdate));
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
            throw new EntityNotFoundException("Some Leagues were not found.");
        }

        return retVal;
    }

    @Override
    public void deletePlayer(String guid) {
        PlayerEntity playerToDelete = this.playerRepository.findByPublicId(guid)
                .orElseThrow(() -> new EntityNotFoundException("Player not found for guid: " + guid));
        
        this.playerRepository.delete(playerToDelete);
    }
}
