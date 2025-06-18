package com.ajp64.hockeytracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "league_teams", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"league_id", "team_id"})
})
public class LeagueTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private LeagueEntity league;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    void setLeague(LeagueEntity league) {
        this.league = league;
    }

    LeagueEntity getLeague() {
     return this.league;
    }

    void setTeam(TeamEntity team) {
        this.team = team;
    }

    TeamEntity getTeam() {
        return this.team;
    }
}

