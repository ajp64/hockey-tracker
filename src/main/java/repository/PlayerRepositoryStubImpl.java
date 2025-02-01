package repository;

import aspects.LogExecution;
import aspects.SecurityCheck;
import model.GameStats;
import model.Player;
import model.Team;
import org.springframework.stereotype.Repository;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class PlayerRepositoryStubImpl implements PlayerRepository {

    private List<Player> playerList = new ArrayList<>();
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
