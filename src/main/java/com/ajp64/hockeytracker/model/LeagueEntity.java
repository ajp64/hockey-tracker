package com.ajp64.hockeytracker.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name="leagues")
public class LeagueEntity extends BaseEntity {

    public LeagueEntity() {}
    private String leagueName;
    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LeaguePlayer> playerMapping = new HashSet<>();

    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LeagueTeam> teamMapping = new HashSet<>();

    public String getTeamName() {
        return leagueName;
    }

    public void setTeamName(String teamName) {
        this.leagueName = teamName;
    }
    @Transient
    public Set<PlayerEntity> getPlayers() {
        return this.playerMapping.stream().map(LeaguePlayer::getPlayer).collect(Collectors.toSet());
    }

    public void setPlayers(Set<PlayerEntity> players) {
        // clear original mappings
        this.playerMapping.clear();

        //set new mappings
        players.forEach(playerEntity -> {
            LeaguePlayer leaguePlayer = new LeaguePlayer();
            leaguePlayer.setLeague(this);
            leaguePlayer.setPlayer(playerEntity);
            this.playerMapping.add(leaguePlayer);
        });
    }

    void addPlayer(PlayerEntity player) {
        LeaguePlayer leaguePlayer = new LeaguePlayer();
        leaguePlayer.setPlayer(player);
        leaguePlayer.setLeague(this);
        this.playerMapping.add(leaguePlayer);
    }

    void removePLayer(PlayerEntity player) {
        this.playerMapping.removeIf(playerMapping -> playerMapping.getPlayer().equals(player));
    }

    @Transient
    public Set<TeamEntity> getTeams() {
        return this.teamMapping.stream().map(LeagueTeam::getTeam).collect(Collectors.toSet());
    }

    public void setTeams(Set<TeamEntity> teams) {
        // clear original mappings
        this.teamMapping.clear();

        //set new mappings
        teams.forEach(teamEntity -> {
            LeagueTeam leagueTeam = new LeagueTeam();
            leagueTeam.setLeague(this);
            leagueTeam.setTeam(teamEntity);
            this.teamMapping.add(leagueTeam);
        });
    }

    void addTeam(TeamEntity team) {
        LeagueTeam leagueTeam = new LeagueTeam();
        leagueTeam.setTeam(team);
        leagueTeam.setLeague(this);
        this.teamMapping.add(leagueTeam);
    }

    void removeTeam(TeamEntity team) {
        this.teamMapping.removeIf(teamMapping -> teamMapping.getTeam().equals(team));
    }

}
