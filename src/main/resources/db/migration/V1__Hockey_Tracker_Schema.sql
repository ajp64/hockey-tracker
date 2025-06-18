CREATE SCHEMA IF NOT EXISTS hockey_tracker;

-- Main Tables
CREATE TABLE IF NOT EXISTS hockey_tracker.players (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_Name VARCHAR(255),
    public_Id VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS hockey_tracker.teams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_Name VARCHAR(255),
    public_Id VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS hockey_tracker.leagues (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    public_id CHAR(36) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Intermediary Tables

CREATE TABLE IF NOT EXISTS hockey_tracker.league_players (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    league_id BIGINT NOT NULL,
    player_id BIGINT NOT NULL,

    CONSTRAINT uq_league_player UNIQUE (league_id, player_id),
    CONSTRAINT fk_league_player_league FOREIGN KEY (league_id) REFERENCES leagues(id),
    CONSTRAINT fk_league_player_player FOREIGN KEY (player_id) REFERENCES players(id)
);

CREATE TABLE IF NOT EXISTS hockey_tracker.league_teams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    league_id BIGINT NOT NULL,
    team_id BIGINT NOT NULL,

    CONSTRAINT uq_league_team UNIQUE (league_id, team_id),
    CONSTRAINT fk_league_team_league FOREIGN KEY (league_id) REFERENCES leagues(id),
    CONSTRAINT fk_league_team_team FOREIGN KEY (team_id) REFERENCES teams(id)
);

CREATE TABLE IF NOT EXISTS hockey_tracker.team_players (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_id BIGINT NOT NULL,
    player_id BIGINT NOT NULL,

    CONSTRAINT uq_team_player UNIQUE (team_id, player_id),
    CONSTRAINT fk_team FOREIGN KEY (team_id) REFERENCES teams(id),
    CONSTRAINT fk_player FOREIGN KEY (player_id) REFERENCES players(id)
);