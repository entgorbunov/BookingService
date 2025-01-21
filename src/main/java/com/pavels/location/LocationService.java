package com.pavels.location;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public List<Location> getAllLocations() {
        List<LocationEntity> locationEntityList = locationRepository.findAll();
        return locationMapper.fromLocationEntityList(locationEntityList);
    }

    public Location getLocationById(Long locationId) {
        LocationEntity locationEntity = locationRepository.findById(locationId)
            .orElseThrow(() -> new EntityNotFoundException("Location entity does not exist id = %s".formatted(locationId)));
        return locationMapper.fromLocationEntity(locationEntity);
    }

    public Location createLocation(Location location) {
        if (location.id() != null) {
            throw new IllegalArgumentException("locationToCreate cannot be created. Id must be empty");
        }
        LocationEntity locationEntity = locationMapper.toLocationEntity(location);
        LocationEntity savedLocationEntity = locationRepository.save(locationEntity);
        return locationMapper.fromLocationEntity(savedLocationEntity);

    }

    public Location updateLocation(Location location, Long locationId) {
        if (location.id() != null) {
            throw new IllegalArgumentException("locationToUpdate cannot be updated. Id must be empty");
        }
        return locationRepository.findById(locationId)
            .map(existingLocation -> {
                existingLocation.setName(location.name());
                existingLocation.setAddress(location.address());
                existingLocation.setCapacity(location.capacity());
                existingLocation.setDescription(location.description());
                LocationEntity updatedLocationEntity = locationRepository.save(existingLocation);
                return locationMapper.fromLocationEntity(updatedLocationEntity);
            })
            .orElseThrow(() -> new EntityNotFoundException("Location entity with id " + locationId + " does not exist"))
            ;
    }

    public void deleteLocation(Long locationId) {
        LocationEntity locationEntity = locationRepository.findById(locationId)
            .orElseThrow(() -> new EntityNotFoundException("Location entity with id " + locationId + " does not exist"));
        locationRepository.delete(locationEntity);
    }


}
