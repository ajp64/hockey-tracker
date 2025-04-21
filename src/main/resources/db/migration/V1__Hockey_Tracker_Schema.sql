CREATE SCHEMA IF NOT EXISTS hockey_tracker;

CREATE TABLE hockey_tracker.players (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_Name VARCHAR(255),
    public_Id VARCHAR(255),
    dob VARCHAR(255),
    position VARCHAR(255)
);

CREATE TABLE hockey_tracker.teams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_Name VARCHAR(255),
    public_Id VARCHAR(255)
);