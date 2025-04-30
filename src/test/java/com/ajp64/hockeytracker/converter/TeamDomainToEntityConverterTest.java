package com.ajp64.hockeytracker.converter;

import com.ajp64.hockeytracker.model.PlayerEntity;
import com.ajp64.hockeytracker.model.TeamEntity;
import com.rest.server.model.Player;
import com.rest.server.model.Team;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamDomainToEntityConverterTest {

    private final TeamDomainToEntityConverter testSubject = new TeamDomainToEntityConverter();

    @Test
    void shouldConvertTeamDomainToEntityCorrectly() {
        Team team = new Team();
        team.setTeamName("teamName");
        team.setPublicId("publicId");

        TeamEntity entity = testSubject.convert(team);

        assertThat(entity.getTeamName()).isEqualTo("teamName");
    }
}
