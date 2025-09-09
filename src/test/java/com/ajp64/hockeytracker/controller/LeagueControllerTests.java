package com.ajp64.hockeytracker.controller;

import com.ajp64.hockeytracker.exceptions.EntityNotFoundException;
import com.ajp64.hockeytracker.service.LeagueService;
import com.rest.server.model.League;
import com.rest.server.model.LeagueListResponse;
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
public class LeagueControllerTests {

    @Mock
    private LeagueService mockLeagueService;

    private LeagueController testSubject;

    @BeforeEach
    void setUp(){
        testSubject = new LeagueController(mockLeagueService);
    }

    @Test
    void testGetLeagues()
    {
        Set<League> expected = Set.of(new League("leagueName"));

        when(mockLeagueService.getLeagues()).thenReturn(expected);

        ResponseEntity<LeagueListResponse> actual = testSubject.getLeagues();

        assertThat(Objects.requireNonNull(actual.getBody()).getLeagueList()).isEqualTo(expected);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetLeagueById()
    {
        League expected = new League("leagueName");

        when(mockLeagueService.getLeague("publicId")).thenReturn(expected);

        ResponseEntity<League> actual = testSubject.getLeagueById("publicId");

        assertThat(Objects.requireNonNull(actual.getBody())).isEqualTo(expected);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetLeagueByIdNotFound()
    {
        String leagueId = "non-existent-id";

        when(mockLeagueService.getLeague(leagueId))
                .thenThrow(new EntityNotFoundException("League not found for guid: " + leagueId));

        ResponseEntity<League> actual = testSubject.getLeagueById(leagueId);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).isNull();
    }

    @Test
    void testCreateLeague()
    {
        League expected = new League("leagueName");

        when(mockLeagueService.createLeague(expected)).thenReturn(expected);

        ResponseEntity<League> actual = testSubject.createLeague(expected);

        assertThat(Objects.requireNonNull(actual.getBody())).isEqualTo(expected);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void testUpdateLeague()
    {
        String leagueId = "test-league-id";
        League updateLeague = new League("updatedLeagueName");
        League expected = new League("updatedLeagueName");

        when(mockLeagueService.updateLeague(leagueId, updateLeague)).thenReturn(expected);

        ResponseEntity<League> actual = testSubject.updateLeague(leagueId, updateLeague);

        assertThat(Objects.requireNonNull(actual.getBody())).isEqualTo(expected);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testUpdateLeagueNotFound()
    {
        String leagueId = "non-existent-id";
        League updateLeague = new League("updatedLeagueName");

        when(mockLeagueService.updateLeague(leagueId, updateLeague))
                .thenThrow(new EntityNotFoundException("League not found for guid: " + leagueId));

        ResponseEntity<League> actual = testSubject.updateLeague(leagueId, updateLeague);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).isNull();
    }

    @Test
    void testDeleteLeague()
    {
        String leagueId = "test-league-id";

        doNothing().when(mockLeagueService).deleteLeague(leagueId);

        ResponseEntity<Void> actual = testSubject.deleteLeague(leagueId);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(actual.getBody()).isNull();
        verify(mockLeagueService).deleteLeague(leagueId);
    }

    @Test
    void testDeleteLeagueNotFound()
    {
        String leagueId = "non-existent-id";

        doThrow(new EntityNotFoundException("League not found for guid: " + leagueId))
                .when(mockLeagueService).deleteLeague(leagueId);

        ResponseEntity<Void> actual = testSubject.deleteLeague(leagueId);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).isNull();
    }
}

