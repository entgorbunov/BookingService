package com.pavels.location;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    builder = @Builder(disableBuilder = true)
)
public interface LocationMapper {

    LocationDto toDto(Location location);

    Location toLocation(LocationDto locationDto);

    Location fromLocationEntity(LocationEntity locationEntity);

    LocationEntity toLocationEntity(Location location);

    List<Location> fromLocationEntityList(List<LocationEntity> locationEntityList);

}
