package com.ajp64.hockeytracker.service;

import com.ajp64.hockeytracker.model.PlayerEntity;
import com.rest.server.model.Player;
import com.rest.server.model.PlayerTeamsUpdate;

import java.util.Set;

public interface PlayerService {

    Player createPlayer(Player newPlayer);
    Player getPlayer(String playerId);
    Player updatePlayer(String guid, Player updatedPlayer);
    Player updatePlayerTeams(String guid, PlayerTeamsUpdate update);
    Set<Player> getPlayers();
    void deletePlayer(String guid);
}
