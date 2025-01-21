package com.pavels.location;

import lombok.Builder;

@Builder
public record Location(
    Long id,
    String name,
    String address,
    Long capacity,
    String description
) {
}
