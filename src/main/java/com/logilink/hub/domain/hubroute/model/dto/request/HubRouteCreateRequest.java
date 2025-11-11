package com.logilink.hub.domain.hubroute.model.dto.request;

import com.logilink.hub.domain.hub.model.entity.Hub;
import com.logilink.hub.domain.hubroute.model.entity.HubRoute;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class HubRouteCreateRequest {

    @NotNull
    private UUID originHubId;

    @NotNull
    private UUID destinationHubId;

    @NotNull
    private Double totalDistance;

    @NotNull
    private Integer totalDuration;

    public HubRoute toEntity(Hub originHub, Hub destinationHub) {
        return HubRoute.builder()
                .originHub(originHub)
                .destinationHub(destinationHub)
                .totalDistance(totalDistance)
                .totalDuration(totalDuration)
                .build();
    }
}
