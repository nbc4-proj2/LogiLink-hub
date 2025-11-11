package com.logilink.hub.domain.hub.controller;

import com.logilink.hub.domain.hub.model.dto.request.HubCreateRequest;
import com.logilink.hub.domain.hub.model.dto.request.HubUpdateRequest;
import com.logilink.hub.domain.hub.model.dto.response.HubResponse;
import com.logilink.hub.domain.hub.model.entity.Hub;
import com.logilink.hub.domain.hub.repository.HubRepository;
import com.logilink.hub.domain.hub.service.HubService;
import com.logilink.hub.domain.hub.service.HubServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/hubs")
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    @PostMapping
    public ResponseEntity<HubResponse> createHub(@Valid @RequestBody HubCreateRequest hubCreateRequest) {
        Hub hub = hubService.createHub(hubCreateRequest.toEntity());
        return ResponseEntity.ok(new HubResponse(hub));
    }

    @PutMapping("/{hubId}")
    public ResponseEntity<HubResponse> updateHub(@PathVariable UUID hubId, @Valid @RequestBody HubUpdateRequest hubUpdateRequest) {
        Hub hub = hubService.updateHub(hubId, hubUpdateRequest.toEntity());
        return ResponseEntity.ok(new HubResponse(hub));
    }

    @DeleteMapping("/{hubId}")
    public ResponseEntity<Void> deleteHub(@PathVariable UUID hubId) {
        hubService.deleteHub(hubId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{hubId}")
    public ResponseEntity<HubResponse> getHub(@PathVariable UUID hubId) {
        Hub hub = hubService.getHub(hubId);
        return ResponseEntity.ok(new HubResponse(hub));
    }

    @GetMapping
    public ResponseEntity<List<HubResponse>> getAllHubs() {
        List<HubResponse> hubs = hubService.getAllHubs()
                .stream()
                .map(HubResponse::new)
                .toList();
        return ResponseEntity.ok(hubs);
    }


}
