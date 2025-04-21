package com.ajp64.hockeytracker.service;

import com.rest.server.model.Player;
import com.ajp64.hockeytracker.model.PlayerEntity;

import java.util.Set;

public interface PlayerService {

    Player createPlayer(Player newPlayer);

    Player getPlayer(String playerId);

    Set<Player> getPlayers();
}
