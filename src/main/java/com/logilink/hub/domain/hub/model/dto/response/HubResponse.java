package com.logilink.hub.domain.hub.model.dto.response;

import com.logilink.hub.domain.hub.model.entity.Hub;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class HubResponse {

    private UUID hubId;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private LocalDateTime createdAt;

    public HubResponse(Hub hub) {
        this.hubId = hub.getId();
        this.name = hub.getName();
        this.address = hub.getAddress();
        this.latitude = hub.getLatitude();
        this.longitude = hub.getLongitude();
        this.createdAt = hub.getCreatedAt();
    }
}