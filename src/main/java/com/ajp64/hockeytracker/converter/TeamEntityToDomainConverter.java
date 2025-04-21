package com.ajp64.hockeytracker.converter;

import com.ajp64.hockeytracker.model.TeamEntity;
import com.rest.server.model.PlayerData;
import com.rest.server.model.Team;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class TeamEntityToDomainConverter {

    private final PlayerEntityToDataConverter playerConverter;

    public TeamEntityToDomainConverter(PlayerEntityToDataConverter playerConverter) {
        this.playerConverter = playerConverter;
    }

    public Team convert(TeamEntity teamEntity) {
        final Team retval = new Team();

        Set<PlayerData> players = teamEntity.getPlayers().stream()
                .map(playerConverter::convert)
                .collect(Collectors.toSet());

        retval.setTeamName(teamEntity.getTeamName());
        retval.setPublicId(teamEntity.getPublicId());
        retval.setPlayers(players);

        return retval;
    }
}