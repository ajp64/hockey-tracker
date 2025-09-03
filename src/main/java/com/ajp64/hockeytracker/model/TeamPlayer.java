package com.ajp64.hockeytracker.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "team_players", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"team_id", "player_id"})
})
public class TeamPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    @ManyToOne(optional = true) // optional, only if team is part of a league
    @JoinColumn(name = "league_id")
    private LeagueEntity league;
    private String role;
    private LocalDate joinedAt;

    public TeamEntity getTeam() {
        return this.team;
    }

    public void setTeam(TeamEntity team){
        this.team = team;
    }

    public PlayerEntity getPlayer(){
        return this.player;
    }

    public void setPlayer(PlayerEntity player){
        this.player = player;
    }

    public LeagueEntity getLeague() {
        return league;
    }

    public void setLeague(LeagueEntity league) {
        this.league = league;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDate joinedAt) {
        this.joinedAt = joinedAt;
    }
}

