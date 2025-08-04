package com.ajp64.hockeytracker.controller;

import com.ajp64.hockeytracker.exceptions.EntityNotFoundException;
import com.rest.server.model.Player;
import com.rest.server.model.PlayerListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ajp64.hockeytracker.service.PlayerService;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
@Validated
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public ResponseEntity<PlayerListResponse> getPlayers() {
        Set<Player> players = playerService.getPlayers();
        return ResponseEntity.ok(new PlayerListResponse(players));
    }

    @GetMapping("/players/{guid}")
    public ResponseEntity<Player> getPlayerById(@PathVariable @NotBlank String guid) {
        try {
            Player player = playerService.getPlayer(guid);
            return ResponseEntity.ok(player);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody @Valid Player player) {
        Player createdPlayer = playerService.createPlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer);
    }

    @PutMapping("/players/{guid}")
    public ResponseEntity<Player> updatePlayer(@PathVariable @NotBlank String guid, 
                                            @RequestBody @Valid Player player) {
        try {
            Player updatedPlayer = playerService.updatePlayer(guid, player);
            return ResponseEntity.ok(updatedPlayer);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/players/{guid}")
    public ResponseEntity<Void> deletePlayer(@PathVariable @NotBlank String guid) {
        try {
            playerService.deletePlayer(guid);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
