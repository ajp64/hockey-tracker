package repository;

import aspects.LogExecution;
import aspects.SecurityCheck;
import model.GameStats;
import model.Player;
import model.Team;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Repository
public class PlayerRepositoryStubImpl implements PlayerRepository {
    public PlayerRepositoryStubImpl() {
    }

    @Override
    @SecurityCheck
    public void storePlayer(Player newPlayer) {
        System.out.println("Stored player: " + newPlayer.toString());
    }

    @Override
    public Player getPlayer(String playerId) {
        ArrayList<GameStats> statList = new ArrayList<>();
        GameStats stats = new GameStats("stats");
        statList.add(stats);
        Team playerTeam = new Team("Dragons");
        Date dob = new Date(2000, Calendar.DECEMBER, 13);

        return new Player("id", "player name", dob, "forward", playerTeam, "player.jpg", statList);
    }
}
