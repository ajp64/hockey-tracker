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
	}

}
