package com.ajp64.hockeytracker.service;

import com.ajp64.hockeytracker.mapper.LeagueMapper;
import com.ajp64.hockeytracker.model.LeagueEntity;
import com.ajp64.hockeytracker.repository.LeagueRepository;
import com.rest.server.model.League;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
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

    public Set<League> getLeagues() {
        return this.leagueRepository.findAll().stream()
                .map(leagueMapper::entityToDomain)
                .collect(Collectors.toSet());
    }

    public League getLeague(String leagueId) {
        LeagueEntity entity = this.leagueRepository.findByPublicId(leagueId);

        return this.leagueMapper.entityToDomain(entity);
    }

    public League createLeague(League league){
        LeagueEntity entity = this.leagueMapper.domainToEntity(league);

        LeagueEntity savedVal = this.leagueRepository.save(entity);

        return this.leagueMapper.entityToDomain(savedVal);
    }
}
