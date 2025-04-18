package com.ajp64.hockeytracker.converter;

import com.ajp64.hockeytracker.model.TeamEntity;
import com.rest.server.model.Player;
import com.rest.server.model.Team;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamEntityConverter {

    private PlayerEntityConverter playerEntityConverter;

//    TeamEntityConverter(PlayerEntityConverter playerEntityConverter) {
//        this.playerEntityConverter = playerEntityConverter;
//    }

    public Team convert(TeamEntity teamEntity)
    {
        final Team retval = new Team();
        List<Player> playerList = teamEntity.getPlayers()
                .stream()
                .map(playerEntity -> playerEntityConverter.convert(playerEntity))
                .toList();

        retval.name(teamEntity.getTeamName());
        retval.publicId(teamEntity.getPublicId());
        retval.players(playerList);

        return retval;
    }


}
