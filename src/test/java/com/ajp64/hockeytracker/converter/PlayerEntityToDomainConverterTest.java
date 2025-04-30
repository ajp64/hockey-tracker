package com.ajp64.hockeytracker.converter;

import com.ajp64.hockeytracker.model.PlayerEntity;
import com.ajp64.hockeytracker.model.TeamEntity;
import com.rest.server.model.Player;
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
public class PlayerEntityToDomainConverterTest {
    @Mock
    private TeamEntityToDataConverter mockConverter;
    private PlayerEntityToDomainConverter testSubject;

    @BeforeEach
    void setUp(){
        testSubject = new PlayerEntityToDomainConverter(mockConverter);
    }

    @Test
    void shouldConvertPlayerEntityToDomainCorrectly() {
        TeamEntity team1 = new TeamEntity();
        team1.setTeamName("Team A");
        team1.setPublicId("idA");

        TeamEntity team2 = new TeamEntity();
        team2.setTeamName("Team B");
        team2.setPublicId("idB");

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setPlayerName("playerName");
        playerEntity.setDob("dob");
        playerEntity.setPosition("Center");
        playerEntity.setPublicId("generatedId");
        playerEntity.setTeams(Set.of(team1, team2));

        when(mockConverter.convert(any(TeamEntity.class))).thenAnswer(invocation -> {
            TeamEntity entity = invocation.getArgument(0);
            TeamData data = new TeamData();
            data.setTeamName(entity.getTeamName());
            data.setPublicId(entity.getPublicId());
            return data;
        });

        Player player = testSubject.convert(playerEntity);

        assertThat(player)
                .isNotNull()
                .extracting(Player::getPlayerName, Player::getDob, Player::getPosition, Player::getPublicId)
                .containsExactly("playerName", "dob", "Center", "generatedId");

        assertThat(player.getTeams())
                .hasSize(2)
                .extracting(TeamData::getTeamName, TeamData::getPublicId)
                .containsExactlyInAnyOrder(
                        tuple("Team A", "idA"),
                        tuple("Team B", "idB")
                );
    }
}
