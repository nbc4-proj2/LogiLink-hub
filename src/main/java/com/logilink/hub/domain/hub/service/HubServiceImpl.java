package com.logilink.hub.domain.hub.service;

import com.logilink.hub.common.exception.HubErrorCode;
import com.logilink.hub.domain.hub.model.dto.request.HubCreateRequest;
import com.logilink.hub.domain.hub.model.dto.request.HubUpdateRequest;
import com.logilink.hub.domain.hub.model.dto.response.HubResponse;
import com.logilink.hub.domain.hub.model.entity.Hub;
import com.logilink.hub.domain.hub.repository.HubRepository;
import com.logilink.hub.domain.hubroute.repository.HubRouteRepository;
import com.sparta.logilinkcommon.common.exception.AppException;
import lombok.RequiredArgsConstructor;
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
public class HubServiceImpl implements HubService {

    private final HubRepository hubRepository;
    private final HubRouteRepository hubRouteRepository;

    @Override
    @Transactional
    public HubResponse createHub(HubCreateRequest hubCreateRequest) {
        if (hubRepository.existsByName(hubCreateRequest.getName())) {
            throw AppException.of(HubErrorCode.HUB_NAME_DUPLICATE);
        }
        Hub saved = hubRepository.save(hubCreateRequest.toEntity());
        return new HubResponse(saved);
    }

    @Override
    @Transactional
    public HubResponse updateHub(UUID hubId, HubUpdateRequest hubUpdateRequest) {
        Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId)
                .orElseThrow(() -> AppException.of(HubErrorCode.HUB_NOT_FOUND));

        // 이름이 바뀌었을 때 그 이름이 이미 사용중이라면 예외
        if (hubUpdateRequest.getName() != null
                && !hubUpdateRequest.getName().equals(hub.getName())
                && hubRepository.existsByName(hubUpdateRequest.getName())) {
            throw AppException.of(HubErrorCode.HUB_NAME_DUPLICATE);
        }
        hub.update(
                hubUpdateRequest.getName(),
                hubUpdateRequest.getAddress(),
                hubUpdateRequest.getLatitude(),
                hubUpdateRequest.getLongitude()
        );

        return new HubResponse(hub);
    }

    @Override
    @Transactional
    public void deleteHub(UUID hubId) {
        Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId)
                .orElseThrow(() -> AppException.of(HubErrorCode.HUB_NOT_FOUND));

        hub.delete(1L); // 나중에 로그인 사용자 ID로 교체

        hubRouteRepository.findActiveByOrigin(hub)
                .forEach(route -> route.delete(1L));// 나중에 로그인 사용자 ID로 교체

        hubRouteRepository.findActiveByDestination(hub)
                .forEach(route -> route.delete(1l));// 나중에 로그인 사용자 ID로 교체

        hub.delete(1L); // 나중에 로그인 사용자 ID로 교체
    }

    @Override
    public HubResponse getHub(UUID hubId) {
         Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId)
                .orElseThrow(() -> AppException.of(HubErrorCode.HUB_NOT_FOUND));
        return new HubResponse(hub);
    }


    @Override
    public Page<HubResponse> getHubPage(String keyword, int page, int size, String sort) {
        List<Integer> allowedSizes = List.of(10, 30, 50);
        int finalSize = allowedSizes.contains(size) ? size : 10;

        String[] sortParam = sort.split(",");
        Sort.Direction direction = sortParam.length > 1 && sortParam[1].equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, finalSize, Sort.by(direction, sortParam[0]));

        Page<Hub> companies = (keyword != null && !keyword.isEmpty())
                ? hubRepository.searchHubs(keyword, pageable)
                : hubRepository.findAllActive(pageable);

        return companies.map(HubResponse::new);
    }

}

