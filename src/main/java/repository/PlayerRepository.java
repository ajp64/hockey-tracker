package repository;
import com.rest.server.model.Player;
import java.util.List;

public interface PlayerRepository {

    void storePlayer(Player newPlayer);
    Player getPlayer(String playerId);
    List<Player> getPlayers();
}
