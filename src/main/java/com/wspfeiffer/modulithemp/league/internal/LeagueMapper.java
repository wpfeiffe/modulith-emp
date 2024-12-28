package com.wspfeiffer.modulithemp.league.internal;

import com.wspfeiffer.modulithemp.league.LeagueDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LeagueMapper {
    League toEntity(LeagueDto leagueDto);
    LeagueDto toDto(League league);
    List<League> toEntity(List<LeagueDto> leagueDto);
    List<LeagueDto> toDto(List<League> league);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    League partialUpdate(LeagueDto leagueDto, @MappingTarget League league);
}
