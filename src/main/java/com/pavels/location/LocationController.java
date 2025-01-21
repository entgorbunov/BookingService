package com.pavels.location;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
@Slf4j
public class LocationController {

    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        log.info("Get request for all locations");
        List<Location> locationList = locationService.getAllLocations();
        List<LocationDto> list = locationList.stream()
            .map(locationMapper::toDto)
            .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<LocationDto> getLocationById(
        @PathVariable Long locationId
    ) {
        log.info("Get location request: locationId {}", locationId);
        Location location = locationService.getLocationById(locationId);
        return ResponseEntity.status(204).body(locationMapper.toDto(location));
    }

    @PostMapping
    public ResponseEntity<LocationDto> createLocation(
        @RequestBody @Valid LocationDto locationDto
    ) {
        log.info("Post location request: locationDto={}", locationDto);
        Location location = locationMapper.toLocation(locationDto);
        Location createdLocation = locationService.createLocation(location);
        return ResponseEntity.status(201).body(locationMapper.toDto(createdLocation));
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<LocationDto> updateLocation(
        @PathVariable Long locationId,
        @RequestBody @Valid LocationDto locationDto
    ) {
        log.info("Update location request: locationId{}, locationDto{}", locationId, locationDto);
        Location location = locationService.updateLocation(locationMapper.toLocation(locationDto), locationId);
        return ResponseEntity.ok(locationMapper.toDto(location));
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long locationId) {
        log.info("Delete location request: locationId{}", locationId);
        locationService.deleteLocation(locationId);
        return ResponseEntity.noContent().build();
    }

}
