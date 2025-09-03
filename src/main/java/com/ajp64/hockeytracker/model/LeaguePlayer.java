package com.ajp64.hockeytracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "league_players", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"league_id", "player_id"})
})
public class LeaguePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private LeagueEntity league;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    private String status;

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public PlayerEntity getPlayer() { return this.player; }

    public void setLeague(LeagueEntity league) {
        this.league = league;
    }

    public LeagueEntity getLeague() { return this.league; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
