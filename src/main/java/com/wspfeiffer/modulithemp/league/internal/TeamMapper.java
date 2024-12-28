package com.wspfeiffer.modulithemp.league.internal;

import com.wspfeiffer.modulithemp.league.TeamDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {LeagueMapper.class})
public interface TeamMapper {
    Team toEntity(TeamDto teamDto);
    TeamDto toDto(Team team);
    List<Team> toEntity(List<TeamDto> teamDto);
    List<TeamDto> toDto(List<Team> team);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Team partialUpdate(TeamDto teamDto, @MappingTarget Team team);
}
