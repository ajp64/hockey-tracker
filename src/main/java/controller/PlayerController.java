package controller;

import model.Player;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.PlayerService;

import java.sql.Date;

@Controller
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("players")
    public String viewPlayers(Model model){
        var players = playerService.getPlayers();
        model.addAttribute("players", players);

        return "display.html";
    }

    @PostMapping("players")
    public String createPlayer(@RequestParam String name,
                               @RequestParam String dob,
                               @RequestParam String team,
                               @RequestParam String position,
                               Model model){
        Player p = new Player(null,null,null,null,null,null,null );
        p.setName(name);
        p.setPosition(position);

        playerService.createPlayer(p);

        var players = playerService.getPlayers();
        model.addAttribute("players", players);

        return "display.html";
    }

}
