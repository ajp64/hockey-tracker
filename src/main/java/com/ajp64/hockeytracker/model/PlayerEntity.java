package com.ajp64.hockeytracker.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "players")
public class PlayerEntity extends BaseEntity {

    @Override
    public String toString() {
        return "Player{" +
                "id='" + getId() + '\'' +
                ", playerName='" + playerName + '\'' +
                ", dob=" + dob +
                ", position='" + position + '\'' +
                ", teams=" + getTeams() +
                ", image='" + image + '\'' +
                '}';
    }

    public PlayerEntity() {}
    private String playerName;
    private String dob;
    private String position;
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeamPlayer> teamMapping = new HashSet<>();
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LeaguePlayer> leagueMapping = new HashSet<>();
    private String image;
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Set<TeamPlayer> getTeamMapping() {
        return teamMapping;
    }

    public void setTeamMapping(Set<TeamPlayer> teamMapping) {
        this.teamMapping = teamMapping;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLeagues(Set<LeagueEntity> leagues) {
        // Clear old league mappings
        this.leagueMapping.clear();

        // Create new mappings
        leagues.forEach(leagueEntity -> {
            LeaguePlayer leaguePlayer = new LeaguePlayer();
            leaguePlayer.setPlayer(this);
            leaguePlayer.setLeague(leagueEntity);
            this.leagueMapping.add(leaguePlayer);
        });
    }
    @Transient
    public Set<LeagueEntity> getLeagues() {
        return leagueMapping.stream()
                .map(LeaguePlayer::getLeague)
                .collect(Collectors.toSet());
    }

    public void addLeague(LeagueEntity league) {
        LeaguePlayer leaguePlayer = new LeaguePlayer();
        leaguePlayer.setPlayer(this);
        leaguePlayer.setLeague(league);
        this.leagueMapping.add(leaguePlayer);
    }

    public void removeLeague(LeaguePlayer league) {
        this.leagueMapping.removeIf(leaguePlayer -> leaguePlayer.getLeague().equals(league));
    }

    public void setTeams(Set<TeamEntity> teams) {
        // Clear old team mappings
        this.teamMapping.clear();

        // Create new mappings
        teams.forEach(teamEntity -> {
            TeamPlayer teamPlayer = new TeamPlayer();
            teamPlayer.setPlayer(this);
            teamPlayer.setTeam(teamEntity);
            this.teamMapping.add(teamPlayer);
        });
    }

    @Transient
    public Set<TeamEntity> getTeams() {
        return teamMapping.stream()
                .map(TeamPlayer::getTeam)
                .collect(Collectors.toSet());
    }

    public void addTeam(TeamEntity team) {
        TeamPlayer teamPlayer = new TeamPlayer();
        teamPlayer.setPlayer(this);
        teamPlayer.setTeam(team);
        this.teamMapping.add(teamPlayer);
    }

    public void removeTeam(TeamEntity team) {
        this.teamMapping.removeIf(teamPlayer -> teamPlayer.getTeam().equals(team));
    }

}
