package repository;
import model.PlayerEntity;

import java.util.List;

public interface PlayerRepository {

    void storePlayer(PlayerEntity newPlayer);
    PlayerEntity getPlayer(String playerId);
    List<PlayerEntity> getPlayers();
}
