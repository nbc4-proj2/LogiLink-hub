package com.logilink.hub.domain.hubroute.model.dto.request;

import com.logilink.hub.domain.hub.model.entity.Hub;
import com.logilink.hub.domain.hubroute.model.entity.HubRoute;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class HubRouteCreateRequest {

    @NotNull(message = "출발 허브 ID는 필수입니다.")
    private UUID originHubId;

    @NotNull(message = "도착 허브 ID는 필수입니다.")
    private UUID destinationHubId;

    @NotNull(message = "총 거리는 필수입니다.")
    private Double totalDistance;

    @NotNull(message = "총 소요 시간은 필수입니다.")
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
