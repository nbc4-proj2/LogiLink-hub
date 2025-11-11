package com.logilink.hub.domain.hub.service;

import com.logilink.hub.domain.hub.model.entity.Hub;

import java.util.List;
import java.util.UUID;

import com.logilink.hub.domain.hub.model.entity.Hub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface HubService {
    Hub createHub(Hub hub);
    Hub updateHub(UUID hubId, Hub updatedHub);
    void deleteHub(UUID hubId);
    Hub getHub(UUID hubId);
    Page<Hub> getHubPage(String keyword, Pageable pageable);
}
