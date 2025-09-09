package com.ajp64.hockeytracker.controller;

import com.ajp64.hockeytracker.exceptions.EntityNotFoundException;
import com.rest.server.model.Player;
import com.rest.server.model.PlayerListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.ajp64.hockeytracker.service.PlayerService;

import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PlayerControllerTests {

    @Mock
    private PlayerService mockPlayerService;
    private PlayerController testSubject;

    @BeforeEach
    void setUp(){
        testSubject = new PlayerController(mockPlayerService);
    }

    @Test
    void testGetPlayers()
    {
        Set<Player> expected = Set.of(new Player("playerName"));

        when(mockPlayerService.getPlayers()).thenReturn(expected);

        ResponseEntity<PlayerListResponse> actual = testSubject.getPlayers();

        assertThat(Objects.requireNonNull(actual.getBody()).getPlayerList()).isEqualTo(expected);
    }

    @Test
    void testGetPlayerById()
    {
        Player expected = new Player("playerName");

        when(mockPlayerService.getPlayer("publicId")).thenReturn(expected);

        ResponseEntity<Player> actual = testSubject.getPlayerById("publicId");

        assertThat(Objects.requireNonNull(actual.getBody())).isEqualTo(expected);
    }

    @Test
    void testCreatePlayer()
    {
        Player expected = new Player("playerName");

        when(mockPlayerService.createPlayer(expected)).thenReturn(expected);

        ResponseEntity<Player> actual = testSubject.createPlayer(expected);

        assertThat(Objects.requireNonNull(actual.getBody())).isEqualTo(expected);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void testUpdatePlayer()
    {
        String playerId = "test-player-id";
        Player updatePlayer = new Player("updatedPlayerName");
        updatePlayer.setPublicId(playerId);
        Player expected = new Player("updatedPlayerName");

        when(mockPlayerService.updatePlayer(playerId, updatePlayer)).thenReturn(expected);

        ResponseEntity<Player> actual = testSubject.updatePlayer(playerId, updatePlayer);

        assertThat(Objects.requireNonNull(actual.getBody())).isEqualTo(expected);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testUpdatePlayerNotFound()
    {
        String playerId = "non-existent-id";
        Player updatePlayer = new Player("updatedPlayerName");
        updatePlayer.setPublicId(playerId);

        when(mockPlayerService.updatePlayer(playerId, updatePlayer))
                .thenThrow(new EntityNotFoundException("Player not found for guid: " + playerId));

        ResponseEntity<Player> actual = testSubject.updatePlayer(playerId, updatePlayer);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).isNull();
    }

    @Test
    void testDeletePlayer()
    {
        String playerId = "test-player-id";

        doNothing().when(mockPlayerService).deletePlayer(playerId);

        ResponseEntity<Void> actual = testSubject.deletePlayer(playerId);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(actual.getBody()).isNull();
        verify(mockPlayerService).deletePlayer(playerId);
    }

    @Test
    void testDeletePlayerNotFound()
    {
        String playerId = "non-existent-id";

        doThrow(new EntityNotFoundException("Player not found for guid: " + playerId))
                .when(mockPlayerService).deletePlayer(playerId);

        ResponseEntity<Void> actual = testSubject.deletePlayer(playerId);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).isNull();
    }

    @Test
    void testGetPlayerByIdNotFound()
    {
        String playerId = "non-existent-id";

        when(mockPlayerService.getPlayer(playerId))
                .thenThrow(new EntityNotFoundException("Player not found for guid: " + playerId));

        ResponseEntity<Player> actual = testSubject.getPlayerById(playerId);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).isNull();
    }

}
