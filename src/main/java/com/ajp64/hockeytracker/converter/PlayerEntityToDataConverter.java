package com.ajp64.hockeytracker.converter;


import com.ajp64.hockeytracker.model.PlayerEntity;
import com.rest.server.model.PlayerData;
import org.springframework.stereotype.Component;

@Component
public class PlayerEntityToDataConverter {

    public PlayerData convert(PlayerEntity playerEntity)
    {
        final PlayerData retval = new PlayerData();

        retval.setPlayerName(playerEntity.getPlayerName());
        retval.setDob(playerEntity.getDob());
        retval.setPosition(playerEntity.getPosition());
        retval.setPublicId(playerEntity.getPublicId());

        return retval;
    }

}
