package com.ajp64.hockeytracker.converter;

import com.ajp64.hockeytracker.model.PlayerEntity;
import com.rest.server.model.Player;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerDomainToEntityConverterTest {

    private final PlayerDomainToEntityConverter testSubject = new PlayerDomainToEntityConverter();

    @Test
    void shouldConvertPlayerDomainToEntityCorrectly() {
        Player player = new Player();
        player.setPlayerName("playerName");
        player.setDob("dob");
        player.setPosition("Center");
        player.setPublicId("generatedId");

        PlayerEntity entity = testSubject.convert(player);

        assertThat(entity)
                .isNotNull()
                .extracting(PlayerEntity::getPlayerName, PlayerEntity::getDob, PlayerEntity::getPosition, PlayerEntity::getPublicId)
                .containsExactly("playerName", "dob", "Center", "generatedId");
    }
}
