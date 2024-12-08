package main;

import model.GameStats;
import model.Player;
import model.Team;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.PlayerService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class HockeyTrackerApplication {

	public static void main(String[] args) {

		SpringApplication.run(HockeyTrackerApplication.class, args);
		var context = new AnnotationConfigApplicationContext(AppConfig.class);

		ArrayList<GameStats> statList = new ArrayList<>();
		GameStats stats = new GameStats("goals");
		statList.add(stats);
		Team playerTeam = new Team("Vipers");
		Date dob = new Date(2001, Calendar.JANUARY, 28);

		Player createPlayer = new Player("id", "player name", dob, "forward", playerTeam, "player.jpg", statList);

		var playerService = context.getBean(PlayerService.class);
		playerService.createPlayer(createPlayer);
	}

}
