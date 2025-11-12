package com.logilink.hub.domain.hubroute.model.dto.response;

import com.logilink.hub.domain.hubroute.model.entity.HubRoute;
import lombok.Getter;

import java.util.UUID;

@Getter
public class HubRouteResponse {

    private final UUID routeId;

    private final UUID originHubId;
    private final String originHubName;
    private final String originHubAddress;

    private final UUID destinationHubId;
    private final String destinationHubName;
    private final String destinationHubAddress;

    private final Double totalDistance;
    private final Integer totalDuration;


    public HubRouteResponse(HubRoute route) {
        this.routeId = route.getId();

        // 출발 허브
        this.originHubId = route.getOriginHub() != null ? route.getOriginHub().getId() : null;
        this.originHubName = route.getOriginHub() != null ? route.getOriginHub().getName() : null;
        this.originHubAddress = route.getOriginHub() != null ? route.getOriginHub().getAddress() : null;

        // 도착 허브
        this.destinationHubId = route.getDestinationHub() != null ? route.getDestinationHub().getId() : null;
        this.destinationHubName = route.getDestinationHub() != null ? route.getDestinationHub().getName() : null;
        this.destinationHubAddress = route.getDestinationHub() != null ? route.getDestinationHub().getAddress() : null;

        this.totalDistance = route.getTotalDistance();
        this.totalDuration = route.getTotalDuration();
    }

}
