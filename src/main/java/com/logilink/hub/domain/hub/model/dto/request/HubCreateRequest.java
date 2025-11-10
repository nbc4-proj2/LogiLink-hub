package com.logilink.hub.domain.hub.model.dto.request;

import com.logilink.hub.domain.hub.model.entity.Hub;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HubCreateRequest {

    @NotBlank(message = "허브 이름은 필수입니다.")
    private String name;

    @NotBlank(message = "허브 주소는 필수입니다.")
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
