package com.logilink.hub.domain.hubroute.service;

import com.logilink.hub.domain.hubroute.model.dto.request.HubRouteCreateRequest;
import com.logilink.hub.domain.hubroute.model.dto.request.HubRouteUpdateRequest;
import com.logilink.hub.domain.hubroute.model.dto.response.HubRouteResponse;
import com.logilink.hub.domain.hubroute.model.entity.HubRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface HubRouteService {
    HubRouteResponse createRoute(HubRouteCreateRequest hubRouteCreateRequest);

    HubRouteResponse updateRoute(UUID routeId, HubRouteUpdateRequest hubRouteUpdateRequest);

    void deleteRoute(UUID routeId);

    HubRouteResponse getRoute(UUID routeId);

    Page<HubRouteResponse> getHubRoutePage(String keyword, int page, int size, String sort);

}
