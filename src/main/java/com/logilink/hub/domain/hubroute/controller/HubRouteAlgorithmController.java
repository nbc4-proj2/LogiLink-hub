package com.logilink.hub.domain.hubroute.controller;

import com.logilink.hub.domain.hub.model.entity.Hub;
import com.logilink.hub.domain.hubroute.service.HubRouteAlgorithmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "HubRoute Algorithm API", description = "허브 간 최단 경로 탐색 API")
@RestController
@RequestMapping("/api/v1/hub-routes/path")
@RequiredArgsConstructor
public class HubRouteAlgorithmController {

    private final HubRouteAlgorithmService hubRouteAlgorithmService;

    @Operation(summary = "최단 경로 탐색", description = "출발 허브와 도착 허브를 지정하여 최단 경로를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<String>> findShortestPath(
            @RequestParam UUID originHubId,
            @RequestParam UUID destinationHubId
    ) {
        List<Hub> path = hubRouteAlgorithmService.findShortestPath(originHubId, destinationHubId);

        List<String> hubNames = path.stream()
                .map(Hub::getName)
                .toList();

        return ResponseEntity.ok(hubNames);
    }
}