package com.logilink.hub.domain.hubroute.model.dto.response;

import com.logilink.hub.domain.hubroute.model.entity.HubRoute;
import lombok.Getter;

import java.util.UUID;

@Getter
public class HubRouteResponse {

    private final UUID routeId;
    private final String originHubName;
    private final String destinationHubName;
    private final Double totalDistance;
    private final Integer totalDuration;

    public HubRouteResponse(HubRoute route) {
        this.routeId = route.getId();
        this.originHubName = route.getOriginHub() != null ? route.getOriginHub().getName() : null;
        this.destinationHubName = route.getDestinationHub() != null ? route.getDestinationHub().getName() : null;
        this.totalDistance = route.getTotalDistance();
        this.totalDuration = route.getTotalDuration();
    }

}
