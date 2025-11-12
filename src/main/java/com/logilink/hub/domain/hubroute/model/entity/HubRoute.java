package com.logilink.hub.domain.hubroute.model.entity;

import com.logilink.hub.domain.hub.model.entity.Hub;
import com.sparta.logilinkcommon.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "p_hub_routes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class HubRoute extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "route_id", updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_hub_id", nullable = false)
    private Hub originHub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_hub_id", nullable = false)
    private Hub destinationHub;

    @Column(name = "total_distance", nullable = false)
    private Double totalDistance;

    @Column(name = "total_duration", nullable = false)
    private Integer totalDuration;

    public void changeHubs(Hub originHub, Hub destinationHub) {
        this.originHub = originHub;
        this.destinationHub = destinationHub;
    }

    public void update(Double totalDistance, Integer totalDuration) {
        if (totalDistance != null) this.totalDistance = totalDistance;
        if (totalDuration != null) this.totalDuration = totalDuration;
    }

    public void delete() {
        softDelete();
    }

}