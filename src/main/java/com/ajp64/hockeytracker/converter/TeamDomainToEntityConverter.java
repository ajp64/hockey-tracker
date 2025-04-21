package com.ajp64.hockeytracker.converter;

import com.ajp64.hockeytracker.model.TeamEntity;
import com.rest.server.model.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamDomainToEntityConverter {
    public TeamEntity convert(Team team) {
        final TeamEntity retval = new TeamEntity();

        retval.setTeamName(team.getTeamName());

        return retval;
    }
}