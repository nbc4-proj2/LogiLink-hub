package com.logilink.hub.domain.hub.model.dto.request;

import com.logilink.hub.domain.hub.model.entity.Hub;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HubUpdateRequest {

    private String name;
    private String address;
    private Double latitude;
    private Double longitude;

    public Hub toEntity() {
        return Hub.builder()
                .name(name)
                .address(address)
                .latitude(latitude)
                .longitude(longitude).build();
    }
}
