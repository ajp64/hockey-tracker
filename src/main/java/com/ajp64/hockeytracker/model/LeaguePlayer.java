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

    void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    PlayerEntity getPlayer() { return this.player; }

    void setLeague(LeagueEntity league) {
        this.league = league;
    }

    LeagueEntity getLeague() { return this.league; }
}
