package com.ajp64.hockeytracker.model;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="leagues")
public class LeagueEntity {

    public LeagueEntity() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String leagueName;
    @Column(unique = true, nullable = false, updatable = false)
    private String publicId;

    @ManyToMany(mappedBy = "leagues")
    private Set<PlayerEntity> players;

    @ManyToMany(mappedBy = "leagues")
    private Set<TeamEntity> teams;

    @PrePersist
    public void generatePublicId() {
        this.publicId = UUID.randomUUID().toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeamName() {
        return leagueName;
    }

    public void setTeamName(String teamName) {
        this.leagueName = teamName;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public Set<PlayerEntity> getPlayers() {
        return players;
    }

    public void setPlayers(Set<PlayerEntity> players) {
        this.players = players;
    }

    public Set<TeamEntity> getTeams() {
        return teams;
    }

    public void setTeams(Set<TeamEntity> teams) {
        this.teams = teams;
    }

}
