package com.ajp64.hockeytracker.converter;

import com.ajp64.hockeytracker.model.PlayerEntity;
import com.ajp64.hockeytracker.model.TeamEntity;
import com.rest.server.model.PlayerData;
import com.rest.server.model.TeamData;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamEntityToDataConverterTest {
    private final TeamEntityToDataConverter testSubject = new TeamEntityToDataConverter();

    @Test
    void shouldConvertTeamEntityToDataCorrectly() {
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTeamName("teamName");
        teamEntity.setPublicId("publicId");
        teamEntity.setPlayers(Set.of(new PlayerEntity(), new PlayerEntity()));

        TeamData teamData = testSubject.convert(teamEntity);

        assertThat(teamData)
                .isNotNull()
                .extracting(TeamData::getTeamName, TeamData::getPublicId)
                .containsExactly("teamName", "publicId");
    }
}
