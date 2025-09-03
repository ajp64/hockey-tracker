package com.ajp64.hockeytracker.service;

import com.rest.server.model.League;

import java.util.Set;

public interface LeagueService {

    Set<League> getLeagues();

    League getLeague(String leagueId);

    League createLeague(League league);

    League updateLeague(String guid, League updatedLeague);

    void deleteLeague(String guid);
}
