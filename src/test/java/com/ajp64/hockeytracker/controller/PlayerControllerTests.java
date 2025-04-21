package com.ajp64.hockeytracker.controller;

import com.rest.server.model.Player;
import com.rest.server.model.PlayerListResponse;
import com.ajp64.hockeytracker.model.PlayerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.ajp64.hockeytracker.service.PlayerService;

import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
    void testViewPlayers()
    {
        Set<Player> expected = Set.of(new Player("publicId", "playerName"));

        when(mockPlayerService.getPlayers()).thenReturn(expected);

        ResponseEntity<PlayerListResponse> actual = testSubject.listPlayers();

        assertThat(Objects.requireNonNull(actual.getBody()).getPlayerList()).isEqualTo(expected);
    }

    @Test
    void testCreatePlayer()
    {
        Player expected = new Player("playerName", "publicId");

        when(mockPlayerService.createPlayer(expected)).thenReturn(expected);

        ResponseEntity<Player> actual = testSubject.createPlayer(expected);

        assertThat(Objects.requireNonNull(actual.getBody())).isEqualTo(expected);
    }

}
