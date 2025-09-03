package com.ajp64.hockeytracker.service;

import com.ajp64.hockeytracker.exceptions.EntityNotFoundException;
import com.ajp64.hockeytracker.mapper.LeagueMapper;
import com.ajp64.hockeytracker.model.LeagueEntity;
import com.ajp64.hockeytracker.repository.LeagueRepository;
import com.rest.server.model.League;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeagueServiceImpl implements LeagueService{

    private final LeagueRepository leagueRepository;
    private final LeagueMapper leagueMapper;
    @Autowired
    public LeagueServiceImpl(LeagueMapper leagueMapper,
                             LeagueRepository leagueRepository)
    {
        this.leagueMapper = leagueMapper;
        this.leagueRepository = leagueRepository;
    }
    @Transactional(readOnly = true)
    public Set<League> getLeagues() {
        return this.leagueRepository.findAll().stream()
                .map(leagueMapper::entityToDomain)
                .collect(Collectors.toSet());
    }
    @Transactional(readOnly = true)
    public League getLeague(String leagueId) {
        LeagueEntity entity = this.leagueRepository.findByPublicId(leagueId)
                .orElseThrow(() -> new EntityNotFoundException("League not found for guid: " + leagueId));

        return this.leagueMapper.entityToDomain(entity);
    }

    public League createLeague(League league){
        LeagueEntity entity = this.leagueMapper.domainToEntity(league);

        LeagueEntity savedVal = this.leagueRepository.save(entity);

        return this.leagueMapper.entityToDomain(savedVal);
    }

    @Override
    public League updateLeague(String guid, League update) {
        LeagueEntity leagueToUpdate = this.leagueRepository.findByPublicId(guid)
                .orElseThrow(() -> new EntityNotFoundException("League not found for guid: " + guid));

        // Update basic properties
        leagueToUpdate.setLeagueName(update.getLeagueName());

        return leagueMapper.entityToDomain(leagueRepository.save(leagueToUpdate));
    }

    @Override
    public void deleteLeague(String guid) {
        LeagueEntity leagueToDelete = this.leagueRepository.findByPublicId(guid)
                .orElseThrow(() -> new EntityNotFoundException("League not found for guid: " + guid));
        
        this.leagueRepository.delete(leagueToDelete);
    }
}
