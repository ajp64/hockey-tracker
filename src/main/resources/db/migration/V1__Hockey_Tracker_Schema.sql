CREATE SCHEMA IF NOT EXISTS hockey_tracker;

CREATE TABLE hockey_tracker.players (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    dob VARCHAR(255),
    position VARCHAR(255)
);