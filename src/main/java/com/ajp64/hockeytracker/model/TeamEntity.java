package com.ajp64.hockeytracker.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name="teams")
public class TeamEntity {

    public TeamEntity() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String teamName;
    @Column(unique = true, nullable = false, updatable = false)
    private String publicId;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeamPlayer> playerMapping = new HashSet<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LeagueTeam> leagueMapping = new HashSet<>();

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

    @Transient
    public Set<PlayerEntity> getPlayers() {
        return this.playerMapping.stream()
                .map(TeamPlayer::getPlayer)
                .collect(Collectors.toSet());
    }

    public void setPlayers(Set<PlayerEntity> players) {
        // clear old mappings
        this.playerMapping.clear();

        //create new mappings
        players.forEach(playerEntity -> {
            TeamPlayer teamPlayer = new TeamPlayer();
            teamPlayer.setPlayer(playerEntity);
            teamPlayer.setTeam(this);
            this.playerMapping.add(teamPlayer);
        });
    }

    void addPlayer(PlayerEntity player) {
        TeamPlayer teamPlayer = new TeamPlayer();
        teamPlayer.setTeam(this);
        teamPlayer.setPlayer(player);
        this.playerMapping.add(teamPlayer);
    }

    void removePlayer(PlayerEntity player) {
        this.playerMapping.removeIf(teamPlayer -> teamPlayer.getPlayer().equals(player));
    }

    @Transient
    public Set<LeagueEntity> getLeagues() {
        return this.leagueMapping.stream()
                .map(LeagueTeam::getLeague)
                .collect(Collectors.toSet());
    }

    public void setLeagues(Set<LeagueEntity> leagues) {
        // clear old mappings
        this.leagueMapping.clear();

        //create new mappings
        leagues.forEach(leagueEntity -> {
            LeagueTeam leagueTeam = new LeagueTeam();
            leagueTeam.setLeague(leagueEntity);
            leagueTeam.setTeam(this);
            this.leagueMapping.add(leagueTeam);
        });
    }

    void addLeague(LeagueEntity league) {
        LeagueTeam leagueTeam = new LeagueTeam();
        leagueTeam.setTeam(this);
        leagueTeam.setLeague(league);
        this.leagueMapping.add(leagueTeam);
    }

    void removeLeague(LeagueEntity league) {
        this.leagueMapping.removeIf(leagueTeam -> leagueTeam.getLeague().equals(league));
    }

}
