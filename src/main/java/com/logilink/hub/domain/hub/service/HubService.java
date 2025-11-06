package com.logilink.hub.domain.hub.service;

import com.logilink.hub.domain.hub.model.entity.Hub;

import java.util.List;
import java.util.UUID;

public interface HubService {
    Hub createHub(Hub hub);
    Hub updateHub(UUID hubId, Hub updatedHub);
    void deleteHub(UUID hubId);
    Hub getHub(UUID hubId);
    List<Hub> getAllHubs();
}
