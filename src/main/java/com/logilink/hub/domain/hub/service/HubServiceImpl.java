package com.logilink.hub.domain.hub.service;

import com.logilink.hub.common.exception.HubErrorCode;
import com.logilink.hub.domain.hub.model.entity.Hub;
import com.logilink.hub.domain.hub.repository.HubRepository;
import com.sparta.logilinkcommon.common.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubServiceImpl implements HubService {

    private final HubRepository hubRepository;

    @Override
    @Transactional
    public Hub createHub(Hub hub) {
        return hubRepository.save(hub);
    }

    @Override
    public Hub updateHub(UUID hubId, Hub updatedHub) {
        Hub existingHub = hubRepository.findByIdAndDeletedAtIsNull(hubId).orElseThrow(() -> AppException.of(HubErrorCode.HUB_NOT_FOUND));
        existingHub.update(updatedHub);
        return hubRepository.save(existingHub);
    }

    @Override
    public void deleteHub(UUID hubId) {
        Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId).orElseThrow(() -> AppException.of(HubErrorCode.HUB_NOT_FOUND));
        hub.delete(1L); // 추후 실제 로그인 유저 ID로 대체

    }

    @Override
    public Hub getHub(UUID hubId) {
        return hubRepository.findById(hubId).orElseThrow(() -> AppException.of(HubErrorCode.HUB_NOT_FOUND));
    }

    @Override
    public List<Hub> getAllHubs() {
        return hubRepository.findAllByDeletedAtIsNull();
    }

}