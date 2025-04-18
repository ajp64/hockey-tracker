package com.ajp64.hockeytracker.model;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="teams")
public class TeamEntity {

    public TeamEntity() {

    }
    public TeamEntity(String teamName) {
        this.teamName = teamName;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String teamName;
    @Column(unique = true, nullable = false, updatable = false)
    private String publicId;

    @ManyToMany(mappedBy = "teams")
    private Set<PlayerEntity> players;

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
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
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

}
