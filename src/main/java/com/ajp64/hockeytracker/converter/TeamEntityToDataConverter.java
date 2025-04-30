package com.ajp64.hockeytracker.converter;

import com.ajp64.hockeytracker.model.TeamEntity;
import com.rest.server.model.TeamData;
import org.springframework.stereotype.Component;

@Component
public class TeamEntityToDataConverter {

    public TeamData convert(TeamEntity teamEntity)
    {
        final TeamData retval = new TeamData();

        retval.setTeamName(teamEntity.getTeamName());
        retval.setPublicId(teamEntity.getPublicId());

        return retval;
    }
}
