package com.ajp64.hockeytracker.converter;

import com.ajp64.hockeytracker.model.PlayerEntity;
import com.rest.server.model.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerDomainToEntityConverter {

    public PlayerEntity convert(Player player) {
        final PlayerEntity retval = new PlayerEntity();

        retval.setPlayerName(player.getPlayerName());
        retval.setDob(player.getDob());
        retval.setPosition(player.getPosition());
        retval.setPublicId(player.getPublicId());

        return retval;
    }
}