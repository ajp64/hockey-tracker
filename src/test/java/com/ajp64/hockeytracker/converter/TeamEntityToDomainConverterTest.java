package com.ajp64.hockeytracker.converter;

import com.ajp64.hockeytracker.model.PlayerEntity;
import com.ajp64.hockeytracker.model.TeamEntity;
import com.rest.server.model.Player;
import com.rest.server.model.PlayerData;
import com.rest.server.model.Team;
import com.rest.server.model.TeamData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TeamEntityToDomainConverterTest {
    @Mock
    private PlayerEntityToDataConverter mockConverter;
    private TeamEntityToDomainConverter testSubject;

    @BeforeEach
    void setUp(){
        testSubject = new TeamEntityToDomainConverter(mockConverter);
    }

    @Test
    void shouldConvertTeamEntityToDomainCorrectly() {
        PlayerEntity player1 = new PlayerEntity();
        player1.setPlayerName("player1");
        player1.setPosition("Defense");
        player1.setPublicId("publicId1");

        PlayerEntity player2 = new PlayerEntity();
        player2.setPlayerName("player2");
        player2.setPosition("Center");
        player2.setPublicId("publicId2");

        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTeamName("teamName");
        teamEntity.setPublicId("generatedId");
        teamEntity.setPlayers(Set.of(player1, player2));

        when(mockConverter.convert(any(PlayerEntity.class))).thenAnswer(invocation -> {
            PlayerEntity entity = invocation.getArgument(0);
            PlayerData data = new PlayerData();
            data.setPlayerName(entity.getPlayerName());
            data.setPublicId(entity.getPublicId());
            data.setPosition(entity.getPosition());
            return data;
        });

        Team team = testSubject.convert(teamEntity);

        assertThat(team)
                .isNotNull()
                .extracting(Team::getTeamName, Team::getPublicId)
                .containsExactly("teamName", "generatedId");

        assertThat(team.getPlayers())
                .hasSize(2)
                .extracting(PlayerData::getPlayerName, PlayerData::getPublicId, PlayerData::getPosition)
                .containsExactlyInAnyOrder(
                        tuple("player1", "publicId1", "Defense"),
                        tuple("player2", "publicId2", "Center")
                );
    }
}
