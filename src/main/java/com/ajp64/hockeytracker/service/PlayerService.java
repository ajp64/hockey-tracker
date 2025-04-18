package com.ajp64.hockeytracker.service;

import com.rest.server.model.Player;
import com.ajp64.hockeytracker.model.PlayerEntity;

import java.util.List;

public interface PlayerService {

    void createPlayer(PlayerEntity newPlayer);

    PlayerEntity getPlayer(String playerId);

    List<Player> getPlayers();
}
