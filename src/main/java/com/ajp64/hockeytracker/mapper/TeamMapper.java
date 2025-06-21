package com.ajp64.hockeytracker.mapper;

import com.ajp64.hockeytracker.model.TeamEntity;
import com.rest.server.model.Team;
import com.rest.server.model.TeamData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    TeamMapper INSTANCE = Mappers.getMapper( TeamMapper.class );
    @Mapping(target = "players", ignore = true)
    @Mapping(target = "leagues", ignore = true)
    TeamEntity domainToEntity(Team domain);
    Team entityToDomain(TeamEntity entity);
    TeamData entityToData(TeamEntity entity);
}

