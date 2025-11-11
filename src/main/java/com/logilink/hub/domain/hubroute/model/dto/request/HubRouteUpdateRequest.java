package com.logilink.hub.domain.hubroute.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HubRouteUpdateRequest {

    private Double totalDistance;
    private Integer totalDuration;
}
