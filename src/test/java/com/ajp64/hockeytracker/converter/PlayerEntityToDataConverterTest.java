package com.ajp64.hockeytracker.converter;

import com.ajp64.hockeytracker.model.PlayerEntity;
import com.ajp64.hockeytracker.model.TeamEntity;
import com.rest.server.model.PlayerData;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerEntityToDataConverterTest {
    private final PlayerEntityToDataConverter testSubject = new PlayerEntityToDataConverter();

    @Test
    void shouldConvertPlayerEntityToDataCorrectly() {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setPlayerName("playerName");
        playerEntity.setDob("dob");
        playerEntity.setPosition("Center");
        playerEntity.setPublicId("generatedId");
        playerEntity.setTeams(Set.of(new TeamEntity(), new TeamEntity()));

        PlayerData playerData = testSubject.convert(playerEntity);

        assertThat(playerData)
                .isNotNull()
                .extracting(PlayerData::getPlayerName, PlayerData::getDob, PlayerData::getPosition, PlayerData::getPublicId)
                .containsExactly("playerName", "dob", "Center", "generatedId");
    }
}
