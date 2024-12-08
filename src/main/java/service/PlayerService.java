package service;

import model.Player;

public interface PlayerService {

    void createPlayer(Player newPlayer);

    Player getPlayer(String playerId);
}
