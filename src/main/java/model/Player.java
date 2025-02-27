package model;

import java.util.ArrayList;
import java.util.Date;

public class Player {

    @Override
    public String toString() {
        return "Player{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", position='" + position + '\'' +
                ", team=" + team +
                ", image='" + image + '\'' +
                ", stats=" + stats +
                '}';
    }

    public Player(String uuid, String name, Date dob, String position, Team team, String image, ArrayList<GameStats> stats) {
        this.uuid = uuid;
        this.name = name;
        this.dob = dob;
        this.position = position;
        this.team = team;
        this.image = image;
        this.stats = stats;
    }

    private String uuid;
    private String name;
    private Date dob;
    private String position;
    private Team team;
    private String image;
    private ArrayList<GameStats> stats;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
