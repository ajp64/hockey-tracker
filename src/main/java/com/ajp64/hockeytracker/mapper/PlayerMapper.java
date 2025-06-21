package com.ajp64.hockeytracker.mapper;

import com.ajp64.hockeytracker.model.PlayerEntity;
import com.rest.server.model.Player;
import com.rest.server.model.PlayerData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    PlayerMapper INSTANCE = Mappers.getMapper( PlayerMapper.class );
    @Mapping(target = "teams", ignore = true)
    @Mapping(target = "leagues", ignore = true)
    PlayerEntity domainToEntity(Player domain);
    Player entityToDomain(PlayerEntity entity);
    PlayerData entityToData(PlayerEntity entity);
}

