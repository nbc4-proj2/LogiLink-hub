package com.logilink.hub.domain.hub.service;

import com.logilink.hub.domain.hub.model.dto.request.HubCreateRequest;
import com.logilink.hub.domain.hub.model.dto.request.HubUpdateRequest;
import com.logilink.hub.domain.hub.model.dto.response.HubResponse;
import java.util.UUID;
import org.springframework.data.domain.Page;


public interface HubService {
    HubResponse createHub(HubCreateRequest hubCreateRequest);
    HubResponse updateHub(UUID hubId, HubUpdateRequest hubUpdateRequest);
    void deleteHub(UUID hubId);
    HubResponse getHub(UUID hubId);
    Page<HubResponse> getHubPage(String keyword, int page, int size, String sort);
}
