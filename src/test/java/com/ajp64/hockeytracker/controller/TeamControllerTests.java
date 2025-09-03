package com.ajp64.hockeytracker.controller;

import com.ajp64.hockeytracker.exceptions.EntityNotFoundException;
import com.ajp64.hockeytracker.service.TeamService;
import com.rest.server.model.Team;
import com.rest.server.model.TeamListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
public class TeamControllerTests {

    @Mock
    private TeamService mockTeamService;

    private TeamController testSubject;

    @BeforeEach
    void setUp(){
        testSubject = new TeamController(mockTeamService);
    }

    @Test
    void testGetTeams()
    {
        Set<Team> expected = Set.of(new Team("teamName"));

        when(mockTeamService.getTeams()).thenReturn(expected);

        ResponseEntity<TeamListResponse> actual = testSubject.listTeams();

        assertThat(Objects.requireNonNull(actual.getBody()).getTeamList()).isEqualTo(expected);
    }

    @Test
    void testGetTeamById()
    {
        Team expected = new Team("teamName");

        when(mockTeamService.getTeam("publicId")).thenReturn(expected);

        ResponseEntity<Team> actual = testSubject.getTeamById("publicId");

        assertThat(Objects.requireNonNull(actual.getBody())).isEqualTo(expected);
    }

    @Test
    void testCreateTeam()
    {
        Team expected = new Team("teamName");

        when(mockTeamService.createTeam(expected)).thenReturn(expected);

        ResponseEntity<Team> actual = testSubject.createTeam(expected);

        assertThat(Objects.requireNonNull(actual.getBody())).isEqualTo(expected);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void testUpdateTeam()
    {
        String teamId = "test-team-id";
        Team updateTeam = new Team("updatedTeamName");
        Team expected = new Team("updatedTeamName");

        when(mockTeamService.updateTeam(teamId, updateTeam)).thenReturn(expected);

        ResponseEntity<Team> actual = testSubject.updateTeam(teamId, updateTeam);

        assertThat(Objects.requireNonNull(actual.getBody())).isEqualTo(expected);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testUpdateTeamNotFound()
    {
        String teamId = "non-existent-id";
        Team updateTeam = new Team("updatedTeamName");

        when(mockTeamService.updateTeam(teamId, updateTeam))
                .thenThrow(new EntityNotFoundException("Team not found for guid: " + teamId));

        ResponseEntity<Team> actual = testSubject.updateTeam(teamId, updateTeam);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).isNull();
    }

    @Test
    void testDeleteTeam()
    {
        String teamId = "test-team-id";

        doNothing().when(mockTeamService).deleteTeam(teamId);

        ResponseEntity<Void> actual = testSubject.deleteTeam(teamId);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(actual.getBody()).isNull();
        verify(mockTeamService).deleteTeam(teamId);
    }

    @Test
    void testDeleteTeamNotFound()
    {
        String teamId = "non-existent-id";

        doThrow(new EntityNotFoundException("Team not found for guid: " + teamId))
                .when(mockTeamService).deleteTeam(teamId);

        ResponseEntity<Void> actual = testSubject.deleteTeam(teamId);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).isNull();
    }

    @Test
    void testGetTeamByIdNotFound()
    {
        String teamId = "non-existent-id";

        when(mockTeamService.getTeam(teamId))
                .thenThrow(new EntityNotFoundException("Team not found for guid: " + teamId));

        ResponseEntity<Team> actual = testSubject.getTeamById(teamId);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).isNull();
    }
}
