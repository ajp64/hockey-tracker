package com.ajp64.hockeytracker.converter;

import com.ajp64.hockeytracker.model.PlayerEntity;
import com.rest.server.model.Player;
import com.rest.server.model.TeamData;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
@Component
public class PlayerEntityToDomainConverter {

    private final TeamEntityToDataConverter teamConverter;

    public PlayerEntityToDomainConverter(TeamEntityToDataConverter teamConverter) {
        this.teamConverter = teamConverter;
    }

    public Player convert(PlayerEntity playerEntity) {
        final Player retval = new Player();

        Set<TeamData> teams = playerEntity.getTeams().stream()
                .map(teamConverter::convert)
                .collect(Collectors.toSet());

        retval.setPlayerName(playerEntity.getPlayerName());
        retval.setDob(playerEntity.getDob());
        retval.setPosition(playerEntity.getPosition());
        retval.setPublicId(playerEntity.getPublicId());
        retval.setTeams(teams);

        return retval;
    }
}