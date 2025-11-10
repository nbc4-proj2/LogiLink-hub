package com.logilink.hub.domain.hub.model.dto.request;

import com.logilink.hub.domain.hub.model.entity.Hub;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HubCreateRequest {

    @NotBlank(message = "허브 이름은 필수입니다.")
    @Size(max = 100, message = "허브 이름은 100자 이하로 입력하세요.")
    private String name;

    @NotBlank(message = "허브 주소는 필수입니다.")
    @Size(max = 255, message = "허브 주소는 255자 이하로 입력하세요")
    private String address;

    @DecimalMin(value = "-90.0", message = "위도는 -90~90 사이어야 합니다.")
    @DecimalMax(value = "90.0", message = "위도는 -90~90 사이어야 합니다.")
    private Double latitude;

    @DecimalMin(value = "-180.0", message = "경도는 -180~180 사이어야 합니다.")
    @DecimalMax(value = "180.0", message = "경도는 -180~180 사이어야 합니다.")
    private Double longitude;

    public Hub toEntity() {
        return Hub.builder()
                .name(name)
                .address(address)
                .latitude(latitude)
                .longitude(longitude).build();
    }
}
