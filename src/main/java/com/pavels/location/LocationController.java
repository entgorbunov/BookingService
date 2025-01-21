package com.pavels.location;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Location Controller", description = "API для управления локациями")
@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
@Slf4j
public class LocationController {

    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @Operation(summary = "Получить все локации")
    @ApiResponse(responseCode = "200", description = "OK",
        content = @Content(mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = LocationDto.class)))
    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        log.info("Get request for all locations");
        List<Location> locationList = locationService.getAllLocations();
        List<LocationDto> list = locationList.stream()
            .map(locationMapper::toDto)
            .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Получить локацию по ID")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "204", description = "NO CONTENT")
    @ApiResponse(responseCode = "404", description = "Location not found")
    @GetMapping("/{locationId}")
    public ResponseEntity<LocationDto> getLocationById(
        @PathVariable Long locationId
    ) {
        log.info("Get location request: locationId {}", locationId);
        Location location = locationService.getLocationById(locationId);
        return ResponseEntity.status(204).body(locationMapper.toDto(location));
    }

    @Operation(summary = "Создать новую локацию")
    @ApiResponse(responseCode = "201", description = "CREATED",
        content = @Content(mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = LocationDto.class)))
    @ApiResponse(responseCode = "400", description = "BAD REQUEST - Invalid input data")
    @PostMapping
    public ResponseEntity<LocationDto> createLocation(
        @RequestBody @Valid LocationDto locationDto
    ) {
        log.info("Post location request: locationDto={}", locationDto);
        Location location = locationMapper.toLocation(locationDto);
        Location createdLocation = locationService.createLocation(location);
        return ResponseEntity.status(201).body(locationMapper.toDto(createdLocation));
    }

    @Operation(summary = "Обновить существующую локацию")
    @ApiResponse(responseCode = "200", description = "OK",
        content = @Content(mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = LocationDto.class)))
    @ApiResponse(responseCode = "400", description = "BAD REQUEST - Invalid input data")
    @ApiResponse(responseCode = "404", description = "Location not found")
    @PutMapping("/{locationId}")
    public ResponseEntity<LocationDto> updateLocation(
        @PathVariable Long locationId,
        @RequestBody @Valid LocationDto locationDto
    ) {
        log.info("Update location request: locationId{}, locationDto{}", locationId, locationDto);
        Location location = locationService.updateLocation(locationMapper.toLocation(locationDto), locationId);
        return ResponseEntity.ok(locationMapper.toDto(location));
    }

    @Operation(summary = "Удалить локацию")
    @ApiResponse(responseCode = "204", description = "NO CONTENT")
    @ApiResponse(responseCode = "404", description = "Location not found")
    @DeleteMapping("/{locationId}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long locationId) {
        log.info("Delete location request: locationId{}", locationId);
        locationService.deleteLocation(locationId);
        return ResponseEntity.noContent().build();
    }

}
