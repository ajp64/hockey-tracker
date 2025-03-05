package controller;

import com.rest.server.api.PlayersApi;
import com.rest.server.model.Player;
import com.rest.server.model.PlayerListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.PlayerService;

@RestController
public class PlayerController implements PlayersApi {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("players")
    public ResponseEntity<PlayerListResponse> listPlayers(){
        var players = playerService.getPlayers();

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new PlayerListResponse(players));
    }

    @PostMapping("players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player){
        playerService.createPlayer(player);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(player);
    }
}
