package controller;

import model.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.PlayerService;

import java.util.List;

@RestController
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("players")
    public ResponseEntity<List<Player>> viewPlayers(){
        var players = playerService.getPlayers();

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(players);
    }

    @PostMapping("players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player){
        playerService.createPlayer(player);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(player);
    }

}
