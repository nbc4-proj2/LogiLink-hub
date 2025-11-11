package com.logilink.hub.domain.hubroute.controller;

import com.logilink.hub.domain.hubroute.model.dto.request.HubRouteCreateRequest;
import com.logilink.hub.domain.hubroute.model.dto.request.HubRouteUpdateRequest;
import com.logilink.hub.domain.hubroute.model.dto.response.HubRouteResponse;
import com.logilink.hub.domain.hubroute.service.HubRouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/hub-routes")
@RequiredArgsConstructor
public class HubRouteController {

    private final HubRouteService hubRouteService;

    @PostMapping
    public ResponseEntity<HubRouteResponse> createRoute(@Valid @RequestBody HubRouteCreateRequest request) {
        return ResponseEntity.status(201).body(hubRouteService.createRoute(request));
    }

    @PutMapping("/{routeId}")
    public ResponseEntity<HubRouteResponse> updateRoute(
            @PathVariable UUID routeId,
            @Valid @RequestBody HubRouteUpdateRequest request
    ) {
        return ResponseEntity.ok(hubRouteService.updateRoute(routeId, request));
    }

    @DeleteMapping("/{routeId}")
    public ResponseEntity<Void> deleteRoute(@PathVariable UUID routeId) {
        hubRouteService.deleteRoute(routeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<HubRouteResponse> getRoute(@PathVariable UUID routeId) {
        return ResponseEntity.ok(hubRouteService.getRoute(routeId));
    }

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


