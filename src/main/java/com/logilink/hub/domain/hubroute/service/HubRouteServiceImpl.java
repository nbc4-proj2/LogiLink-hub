package com.logilink.hub.domain.hubroute.service;

import com.logilink.hub.common.exception.HubErrorCode;
import com.logilink.hub.domain.hub.model.entity.Hub;
import com.logilink.hub.domain.hub.repository.HubRepository;
import com.logilink.hub.domain.hubroute.model.dto.request.HubRouteCreateRequest;
import com.logilink.hub.domain.hubroute.model.dto.request.HubRouteUpdateRequest;
import com.logilink.hub.domain.hubroute.model.dto.response.HubRouteResponse;
import com.logilink.hub.domain.hubroute.model.entity.HubRoute;
import com.logilink.hub.domain.hubroute.repository.HubRouteRepository;
import com.sparta.logilinkcommon.common.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubRouteServiceImpl implements HubRouteService {

    private final HubRouteRepository hubRouteRepository;
    private final HubRepository hubRepository;

    @Override
    @Transactional
    @CacheEvict(value = "hubRoutes", key = "'allGraph'")
    public HubRouteResponse createRoute(HubRouteCreateRequest hubRouteCreateRequest) {
        Hub origin = hubRepository.findByIdAndDeletedAtIsNull(hubRouteCreateRequest.getOriginHubId())
                .orElseThrow(() -> AppException.of(HubErrorCode.HUB_NOT_FOUND));
        Hub destination = hubRepository.findByIdAndDeletedAtIsNull(hubRouteCreateRequest.getDestinationHubId())
                .orElseThrow(() -> AppException.of(HubErrorCode.HUB_NOT_FOUND));

        // 중복 경로 방지
        if (hubRouteRepository.findActiveRoute(origin, destination).isPresent()) {
            throw AppException.of(HubErrorCode.HUB_ROUTE_DUPLICATE);
        }

        HubRoute hubRoute = hubRouteCreateRequest.toEntity(origin, destination);
        HubRoute saved = hubRouteRepository.save(hubRoute);

        return new HubRouteResponse(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = "hubRoutes", key = "'allGraph'")
    public HubRouteResponse updateRoute(UUID routeId, HubRouteUpdateRequest hubRouteUpdateRequest) {
        HubRoute existingRoute = hubRouteRepository.findByIdAndDeletedAtIsNull(routeId)
                .orElseThrow(() -> AppException.of(HubErrorCode.HUB_ROUTE_NOT_FOUND));

        existingRoute.update(hubRouteUpdateRequest.getTotalDistance(), hubRouteUpdateRequest.getTotalDuration());

        return new HubRouteResponse(existingRoute);
    }

    @Override
    @Transactional
    @CacheEvict(value = "hubRoutes", key = "'allGraph'")
    public void deleteRoute(UUID routeId) {
        HubRoute hubRoute = hubRouteRepository.findByIdAndDeletedAtIsNull(routeId)
                .orElseThrow(() -> AppException.of(HubErrorCode.HUB_ROUTE_NOT_FOUND));

        hubRoute.delete();
    }

    @Override
    public HubRouteResponse getRoute(UUID routeId) {
        HubRoute route = hubRouteRepository.findByIdAndDeletedAtIsNull(routeId)
                .orElseThrow(() -> AppException.of(HubErrorCode.HUB_ROUTE_NOT_FOUND));

        return new HubRouteResponse(route);
    }

    @Override
    public Page<HubRouteResponse> getHubRoutePage(String keyword, int page, int size, String sort) {
        List<Integer> allowedSizes = List.of(10, 30, 50);
        int finalSize = allowedSizes.contains(size) ? size : 10;

        String[] sortParam = sort.split(",");
        Sort.Direction direction = sortParam.length > 1 && sortParam[1].equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, finalSize, Sort.by(direction, sortParam[0]));

        Page<HubRoute> routes = (keyword != null && !keyword.isEmpty())
                ? hubRouteRepository.searchRoutes(keyword, pageable)
                : hubRouteRepository.findAllActive(pageable);

        return routes.map(HubRouteResponse::new);
    }
}

