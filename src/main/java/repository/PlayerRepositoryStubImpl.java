package repository;

import aspects.SecurityCheck;
import com.rest.server.model.Player;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayerRepositoryStubImpl implements PlayerRepository {

    private final List<Player> playerList = new ArrayList<>();
    public PlayerRepositoryStubImpl() {
    }

    @Override
    @SecurityCheck
    public void storePlayer(Player newPlayer) {
        playerList.add(newPlayer);
    }

    @Override
    public Player getPlayer(String playerId) {
        return playerList.get(0);
    }

    @Override
    public List<Player> getPlayers(){
        return playerList;
    }
}
