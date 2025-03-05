package service;

import com.rest.server.model.Player;

import java.util.List;

public interface PlayerService {

    void createPlayer(Player newPlayer);

    Player getPlayer(String playerId);

    List<Player> getPlayers();
}
