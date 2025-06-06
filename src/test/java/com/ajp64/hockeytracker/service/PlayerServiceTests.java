package com.ajp64.hockeytracker.service;

import com.ajp64.hockeytracker.exceptions.EntityNotFoundException;
import com.ajp64.hockeytracker.exceptions.NoNameException;
import com.ajp64.hockeytracker.mapper.PlayerMapper;
import com.ajp64.hockeytracker.model.PlayerEntity;
import com.ajp64.hockeytracker.repository.PlayerRepository;
import com.ajp64.hockeytracker.repository.TeamRepository;
import com.rest.server.model.Player;
import com.rest.server.model.TeamData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
public class PlayerServiceTests {
    @Mock
    PlayerService testSubject;
    @Mock
    PlayerRepository mockPlayerRepository;
    @Mock
    TeamRepository mockTeamRepository;
    @Mock
    PlayerMapper mockPlayerMapper;

    @BeforeEach
    void setUp(){
        testSubject = new PlayerServiceImpl(
                mockPlayerRepository,
                mockTeamRepository,
                mockPlayerMapper)
        {};
    }

    @Test
    void testCreatePlayer()
    {
        Player createRequest = new Player("playerName");
        Player expected = new Player("playerName");
        expected.setPublicId("generatedId");

        PlayerEntity convertedRequest = new PlayerEntity();
        convertedRequest.setPublicId("generatedId");

        when(mockPlayerMapper.domainToEntity(createRequest)).thenReturn(convertedRequest);
        when(mockPlayerRepository.save(convertedRequest)).thenReturn(convertedRequest);
        when(mockPlayerMapper.entityToDomain(convertedRequest)).thenReturn(expected);

        Player actual = testSubject.createPlayer(createRequest);

        assertThat(actual).isEqualTo(expected);
    }
    @Test
    void testCreatePlayerWithoutName()
    {
        Player createRequest = new Player();

        assertThrows(NoNameException.class, () -> {
            testSubject.createPlayer(createRequest);
        });
    }

    @Test
    void testCreatePlayerWithInvalidTeam()
    {
        Player createRequest = new Player("teamName");
        createRequest.setTeams(Set.of(new TeamData("teamName", "invalidId")));

        when(mockTeamRepository.findByPublicId("invalidId")).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            testSubject.createPlayer(createRequest);
        });

        assertEquals("Team not found with id: invalidId", exception.getMessage());
    }

    @Test
    void testGetPlayer()
    {
        Player expected = new Player("playerName");
        expected.setPublicId("generatedId");

        PlayerEntity returnedEntity = new PlayerEntity();
        returnedEntity.setPublicId("generatedId");
        returnedEntity.setPlayerName("playerName");

        when(mockPlayerRepository.findByPublicId("generatedId")).thenReturn(returnedEntity);
        when(mockPlayerMapper.entityToDomain(returnedEntity)).thenReturn(expected);

        Player actual = testSubject.getPlayer("generatedId");

        assertThat(actual).isEqualTo(expected);
    }
    @Test
    void testGetPlayers()
    {
        Set<Player> expected = Set.of(new Player("playerName1"), new Player("playerName2"));

        PlayerEntity returnedEntity1 = new PlayerEntity();
        PlayerEntity returnedEntity2 = new PlayerEntity();

        returnedEntity1.setPlayerName("playerName1");
        returnedEntity2.setPlayerName("playerName2");

        List<PlayerEntity> returnedEntities = List.of(returnedEntity1, returnedEntity2);

        when(mockPlayerMapper.entityToDomain(any(PlayerEntity.class))).thenAnswer(invocation -> {
            PlayerEntity entity = invocation.getArgument(0);
            return new Player(entity.getPlayerName());
        });
        when(mockPlayerRepository.findAll()).thenReturn(returnedEntities);

        Set<Player> actual = testSubject.getPlayers();

        assertThat(actual).isEqualTo(expected);
    }
}
