package com.logilink.hub.domain.hub.model.entity;

import com.sparta.logilinkcommon.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_hubs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Hub extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "hub_id", updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    @Column(name = "hub_name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "hub_address", length = 255, nullable = false)
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    public void update(String name, String address, Double latitude, Double longitude) {
        if (name != null) this.name = name;
        if (address != null) this.address = address;
        if (latitude != null) this.latitude = latitude;
        if (longitude != null) this.longitude = longitude;
    }

    public void delete() {
        softDelete();
    }

}
