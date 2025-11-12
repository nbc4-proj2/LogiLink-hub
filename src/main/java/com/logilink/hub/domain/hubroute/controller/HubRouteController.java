package com.logilink.hub.domain.hubroute.controller;

import com.logilink.hub.domain.hubroute.model.dto.request.HubRouteCreateRequest;
import com.logilink.hub.domain.hubroute.model.dto.request.HubRouteUpdateRequest;
import com.logilink.hub.domain.hubroute.model.dto.response.HubRouteResponse;
import com.logilink.hub.domain.hubroute.service.HubRouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "HubRoute API", description = "허브 간 이동 경로 관리 API")
@RestController
@RequestMapping("/api/v1/hub-routes")
@RequiredArgsConstructor
public class HubRouteController {

    private final HubRouteService hubRouteService;

    @Operation(summary = "허브 경로 생성", description = "출발 허브와 도착 허브를 지정해 새로운 이동 경로 생성")
    @PreAuthorize("hasAnyRole('MASTER_ADMIN', 'SCHEDULER')")
    @PostMapping
    public ResponseEntity<HubRouteResponse> createRoute(@Valid @RequestBody HubRouteCreateRequest request) {
        return ResponseEntity.status(201).body(hubRouteService.createRoute(request));
    }

    @Operation(summary = "허브 경로 수정", description = "기존 이동 경로의 거리 및 시간 수정")
    @PreAuthorize("hasAnyRole('MASTER_ADMIN', 'SCHEDULER')")
    @PutMapping("/{routeId}")
    public ResponseEntity<HubRouteResponse> updateRoute(
            @PathVariable UUID routeId,
            @Valid @RequestBody HubRouteUpdateRequest request
    ) {
        return ResponseEntity.ok(hubRouteService.updateRoute(routeId, request));
    }

    @Operation(summary = "허브 경로 삭제", description = "허브 경로를 논리적으로 삭제")
    @PreAuthorize("hasAnyRole('MASTER_ADMIN', 'SCHEDULER')")
    @DeleteMapping("/{routeId}")
    public ResponseEntity<Void> deleteRoute(@PathVariable UUID routeId) {
        hubRouteService.deleteRoute(routeId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "허브 경로 단건 조회", description = "허브 경로 ID로 단일 조회")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{routeId}")
    public ResponseEntity<HubRouteResponse> getRoute(@PathVariable UUID routeId) {
        return ResponseEntity.ok(hubRouteService.getRoute(routeId));
    }

    @Operation(summary = "허브 경로 전체 조회", description = "검색어, 페이지, 정렬 조건을 기준으로 허브 경로 조회")
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<Page<HubRouteResponse>> getHubRoutePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort,
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(hubRouteService.getHubRoutePage(keyword, page, size, sort));
    }
}


