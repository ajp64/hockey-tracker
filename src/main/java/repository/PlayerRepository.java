package repository;

import model.Player;

public interface PlayerRepository {

    void storePlayer(Player newPlayer);
    Player getPlayer(String playerId);
}
