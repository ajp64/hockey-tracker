package com.ajp64.hockeytracker.service;

import com.ajp64.hockeytracker.converter.TeamDomainToEntityConverter;
import com.ajp64.hockeytracker.converter.TeamEntityToDomainConverter;
import com.ajp64.hockeytracker.exceptions.EntityNotFoundException;
import com.ajp64.hockeytracker.exceptions.NoNameException;
import com.ajp64.hockeytracker.model.TeamEntity;
import com.ajp64.hockeytracker.repository.PlayerRepository;
import com.ajp64.hockeytracker.repository.TeamRepository;
import com.rest.server.model.PlayerData;
import com.rest.server.model.Team;
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
public class TeamServiceTests {
    @Mock
    TeamService testSubject;
    @Mock
    TeamRepository mockTeamRepository;
    @Mock
    PlayerRepository mockPlayerRepository;
    @Mock
    TeamEntityToDomainConverter mockEntityConverter;
    @Mock
    TeamDomainToEntityConverter mockDomainConverter;

    @BeforeEach
    void setUp(){
        testSubject = new TeamServiceImpl(mockTeamRepository,
                                          mockPlayerRepository,
                                          mockEntityConverter,
                                          mockDomainConverter)
        {};
    }

    @Test
    void testCreateTeam()
    {
        Team createRequest = new Team("teamName");
        Team expected = new Team("teamName");
        expected.setPublicId("generatedId");

        TeamEntity convertedRequest = new TeamEntity();
        convertedRequest.setPublicId("generatedId");

        when(mockDomainConverter.convert(createRequest)).thenReturn(convertedRequest);
        when(mockTeamRepository.save(convertedRequest)).thenReturn(convertedRequest);
        when(mockEntityConverter.convert(convertedRequest)).thenReturn(expected);

        Team actual = testSubject.createTeam(createRequest);

        assertThat(actual).isEqualTo(expected);
    }
    @Test
    void testCreateTeamWithoutName()
    {
        Team createRequest = new Team();

        assertThrows(NoNameException.class, () -> {
            testSubject.createTeam(createRequest);
        });
    }

    @Test
    void testCreateTeamWithInvalidPlayer()
    {
        Team createRequest = new Team("teamName");
        createRequest.setPlayers(Set.of(new PlayerData("playerName", "invalidId")));

        when(mockPlayerRepository.findByPublicId("invalidId")).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            testSubject.createTeam(createRequest);
        });

        assertEquals("Player not found with id: invalidId", exception.getMessage());
    }

    @Test
    void testGetTeam()
    {
        Team expected = new Team("teamName");
        expected.setPublicId("generatedId");

        TeamEntity returnedEntity = new TeamEntity();
        returnedEntity.setPublicId("generatedId");
        returnedEntity.setTeamName("teamName");

        when(mockTeamRepository.findByPublicId("generatedId")).thenReturn(returnedEntity);
        when(mockEntityConverter.convert(returnedEntity)).thenReturn(expected);

        Team actual = testSubject.getTeam("generatedId");

        assertThat(actual).isEqualTo(expected);
    }
    @Test
    void testGetTeams()
    {
        Set<Team> expected = Set.of(new Team("teamName1"), new Team("teamName2"));

        TeamEntity returnedEntity1 = new TeamEntity();
        TeamEntity returnedEntity2 = new TeamEntity();

        returnedEntity1.setTeamName("teamName1");
        returnedEntity2.setTeamName("teamName2");

        List<TeamEntity> returnedEntities = List.of(returnedEntity1, returnedEntity2);

        when(mockEntityConverter.convert(any(TeamEntity.class))).thenAnswer(invocation -> {
            TeamEntity entity = invocation.getArgument(0);
            return new Team(entity.getTeamName());
        });
        when(mockTeamRepository.findAll()).thenReturn(returnedEntities);

        Set<Team> actual = testSubject.getTeams();

        assertThat(actual).isEqualTo(expected);
    }

}
