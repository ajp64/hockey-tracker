package com.ajp64.hockeytracker.converter;

import com.rest.server.model.Player;
import com.ajp64.hockeytracker.model.PlayerEntity;
import com.rest.server.model.Team;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PlayerEntityConverter {

    private TeamEntityConverter teamEntityConverter;

    public Player convert(PlayerEntity playerEntity)
    {
        final Player retval = new Player();

        Set<Team> teamList = playerEntity.getTeams()
                .stream()
                .map(teamEntity -> teamEntityConverter.convert(teamEntity))
                .collect(Collectors.toSet());

        retval.name(playerEntity.getName());
        retval.dob(playerEntity.getDob());
        retval.position(playerEntity.getPosition());
        retval.publicId(playerEntity.getPublicId());
        retval.teams(teamList);

        return retval;
    }

}
