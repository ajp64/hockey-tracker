package com.ajp64.hockeytracker.service;

import com.ajp64.hockeytracker.aspects.LogExecution;
import com.ajp64.hockeytracker.exceptions.EntityNotFoundException;
import com.ajp64.hockeytracker.mapper.TeamMapper;
import com.ajp64.hockeytracker.model.LeagueEntity;
import com.ajp64.hockeytracker.model.PlayerEntity;
import com.ajp64.hockeytracker.model.TeamEntity;
import com.ajp64.hockeytracker.repository.LeagueRepository;
import com.ajp64.hockeytracker.repository.PlayerRepository;
import com.ajp64.hockeytracker.repository.TeamRepository;
import com.ajp64.hockeytracker.exceptions.NoNameException;
import com.rest.server.model.LeagueData;
import com.rest.server.model.PlayerData;
import com.rest.server.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final LeagueRepository leagueRepository;
    private final TeamMapper teamMapper;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository,
                           PlayerRepository playerRepository,
                           LeagueRepository leagueRepository,
                           TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.leagueRepository = leagueRepository;
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
        teamEntity.setLeagues(getLeaguesForTeam(newTeam));

        TeamEntity savedVal = this.teamRepository.save(teamEntity);

        return teamMapper.entityToDomain(savedVal);
    }

    @Override
    @Transactional(readOnly = true)
    public Team getTeam(String teamId)
    {
        TeamEntity teamEntity = this.teamRepository.findByPublicId(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found for guid: " + teamId));
        
        return teamMapper.entityToDomain(teamEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Team> getTeams() {

        return this.teamRepository.findAll()
                .stream().map(teamMapper::entityToDomain)
                .collect(Collectors.toSet());
    }

    private Set<PlayerEntity> getPlayersForTeam(Team team) {
        final Set<String> playerIds = team.getPlayers().stream()
                .map(PlayerData::getPublicId).collect(Collectors.toSet());

        Set<PlayerEntity> retVal =  playerRepository.findAllByPublicIdIn(playerIds);

        if (retVal.size() != playerIds.size()) {
            throw new EntityNotFoundException("Some Players were not found.");
        }

        return retVal;
    }

    private Set<LeagueEntity> getLeaguesForTeam(Team team) {
        final Set<String> leagueIds = team.getLeagues().stream()
                .map(LeagueData::getPublicId).collect(Collectors.toSet());

        Set<LeagueEntity> retVal = leagueRepository.findAllByPublicIdIn(leagueIds);

        if (retVal.size() != leagueIds.size()) {
            throw new EntityNotFoundException("Some Leagues were not found.");
        }

        return retVal;
    }

    @Override
    public Team updateTeam(String guid, Team update) {
        TeamEntity teamToUpdate = this.teamRepository.findByPublicId(guid)
                .orElseThrow(() -> new EntityNotFoundException("Team not found for guid: " + guid));

        // Update basic properties
        teamToUpdate.setTeamName(update.getTeamName());
        teamToUpdate.setPlayers(getPlayersForTeam(update));
        teamToUpdate.setLeagues(getLeaguesForTeam(update));

        return teamMapper.entityToDomain(teamRepository.save(teamToUpdate));
    }

    @Override
    public void deleteTeam(String guid) {
        TeamEntity teamToDelete = this.teamRepository.findByPublicId(guid)
                .orElseThrow(() -> new EntityNotFoundException("Team not found for guid: " + guid));
        
        this.teamRepository.delete(teamToDelete);
    }
}
