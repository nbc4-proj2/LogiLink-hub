package com.logilink.hub.domain.hub.controller;

import com.logilink.hub.domain.hub.model.dto.request.HubCreateRequest;
import com.logilink.hub.domain.hub.model.dto.request.HubUpdateRequest;
import com.logilink.hub.domain.hub.model.dto.response.HubResponse;
import com.logilink.hub.domain.hub.model.entity.Hub;
import com.logilink.hub.domain.hub.repository.HubRepository;
import com.logilink.hub.domain.hub.service.HubService;
import com.logilink.hub.domain.hub.service.HubServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Hub API", description = "허브 관리 API")
@RestController
@RequestMapping("/api/v1/hubs")
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    @Operation(summary = "허브 생성", description = "새로운 허브 등록")
    @PostMapping
    public ResponseEntity<HubResponse> createHub(@Valid @RequestBody HubCreateRequest hubCreateRequest) {
        return ResponseEntity.ok(hubService.createHub(hubCreateRequest));
    }

    @Operation(summary = "허브 수정", description = "허브의 이름, 주소, 위도, 경도 수정")
    @PutMapping("/{hubId}")
    public ResponseEntity<HubResponse> updateHub(@PathVariable UUID hubId, @Valid @RequestBody HubUpdateRequest hubUpdateRequest) {
        return ResponseEntity.ok(hubService.updateHub(hubId, hubUpdateRequest));
    }

    @Operation(summary = "허브 삭제", description = "허브를 논리적으로 삭제")
    @DeleteMapping("/{hubId}")
    public ResponseEntity<Void> deleteHub(@PathVariable UUID hubId) {
        hubService.deleteHub(hubId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "허브 단건 조회", description = "허브 ID로 단일 조회")
    @GetMapping("/{hubId}")
    public ResponseEntity<HubResponse> getHub(@PathVariable UUID hubId) {
        return ResponseEntity.ok(hubService.getHub(hubId));
    }

    @Operation(summary = "허브 전체 조회", description = "검색어, 페이지, 정렬 조건을 기준으로 허브 조회")
    @GetMapping
    public ResponseEntity<Page<HubResponse>> getHubPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort,
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(hubService.getHubPage(keyword, page, size, sort));

    }

}
