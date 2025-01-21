package com.pavels.location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "locations")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "capacity")
    private Long capacity;

    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationEntity that = (LocationEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(capacity, that.capacity) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, capacity, description);
    }

    @Override
    public String toString() {
        return "LocationEntity{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", address='" + address + '\'' +
               ", capacity=" + capacity +
               ", description='" + description + '\'' +
               '}';
    }
}
