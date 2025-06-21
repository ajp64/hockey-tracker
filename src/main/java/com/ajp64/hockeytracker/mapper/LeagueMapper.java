package com.ajp64.hockeytracker.mapper;

import com.ajp64.hockeytracker.model.LeagueEntity;
import com.ajp64.hockeytracker.model.PlayerEntity;
import com.rest.server.model.League;
import com.rest.server.model.LeagueData;
import com.rest.server.model.Player;
import com.rest.server.model.PlayerData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LeagueMapper {
    LeagueMapper INSTANCE = Mappers.getMapper( LeagueMapper.class );
    @Mapping(target = "teams", ignore = true)
    @Mapping(target = "players", ignore = true)
    LeagueEntity domainToEntity(League domain);
    League entityToDomain(LeagueEntity entity);
    LeagueData entityToData(LeagueEntity entity);
}
