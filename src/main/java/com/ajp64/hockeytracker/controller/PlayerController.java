package com.ajp64.hockeytracker.controller;

import com.rest.server.model.Player;
import com.rest.server.model.PlayerListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ajp64.hockeytracker.service.PlayerService;

@RestController
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("players")
    public ResponseEntity<PlayerListResponse> getPlayers(){
        var players = playerService.getPlayers();

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new PlayerListResponse(players));
    }

    @GetMapping("players/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable String id){
        var player = playerService.getPlayer(id);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(player);
    }

    @PostMapping("players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player){
        Player retval = playerService.createPlayer(player);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(retval);
    }
}
