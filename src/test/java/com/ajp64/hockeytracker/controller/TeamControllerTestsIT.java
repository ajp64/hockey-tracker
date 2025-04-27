package com.ajp64.hockeytracker.controller;

import com.ajp64.hockeytracker.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.server.model.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeamController.class)
public class TeamControllerTestsIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListTeams() throws Exception {
        Set<Team> mockTeams = Set.of(new Team("Team A"), new Team("Team B"));
        when(teamService.getTeams()).thenReturn(mockTeams);

        mockMvc.perform(get("/teams"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.teamList").isArray())
                .andExpect(jsonPath("$.teamList[0].publicId").value("1"))
                .andExpect(jsonPath("$.teamList[0].teamName").value("Team A"));
    }

    @Test
    void testGetTeamById() throws Exception {
        Team team = new Team("Team A");
        when(teamService.getTeam("1")).thenReturn(team);

        mockMvc.perform(get("/teams/1"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.publicId").value("1"))
                .andExpect(jsonPath("$.teamName").value("Team A"));
    }

    @Test
    void testCreateTeam() throws Exception {
        Team team = new Team("Team A");
        when(teamService.createTeam(any(Team.class))).thenReturn(team);

        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(team)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.publicId").value("1"))
                .andExpect(jsonPath("$.teamName").value("Team A"));
    }
}
