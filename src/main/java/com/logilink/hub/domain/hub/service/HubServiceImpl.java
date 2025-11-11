package com.logilink.hub.domain.hub.service;

import com.logilink.hub.common.exception.HubErrorCode;
import com.logilink.hub.domain.hub.model.entity.Hub;
import com.logilink.hub.domain.hub.repository.HubRepository;
import com.sparta.logilinkcommon.common.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if (hubRepository.existsByName(hub.getName())) {
            throw AppException.of(HubErrorCode.HUB_NAME_DUPLICATE);
        }
        return hubRepository.save(hub);
    }

    @Override
    @Transactional
    public Hub updateHub(UUID hubId, Hub updatedHub) {
        Hub existingHub = hubRepository.findByIdAndDeletedAtIsNull(hubId)
                .orElseThrow(() -> AppException.of(HubErrorCode.HUB_NOT_FOUND));

        // 이름이 바뀌었을 때 그 이름이 이미 사용중이라면 예외
        if (updatedHub.getName() != null
                && !updatedHub.getName().equals(existingHub.getName())
                && hubRepository.existsByName(updatedHub.getName())) {
            throw AppException.of(HubErrorCode.HUB_NAME_DUPLICATE);
        }
        existingHub.update(updatedHub);
        return existingHub;
    }

    @Override
    public void deleteHub(UUID hubId) {
        Hub hub = hubRepository.findByIdAndDeletedAtIsNull(hubId)
                .orElseThrow(() -> AppException.of(HubErrorCode.HUB_NOT_FOUND));

        hub.delete(1L); // 추후 실제 로그인 유저 ID로 대체

    }

    @Override
    public Hub getHub(UUID hubId) {
        return hubRepository.findByIdAndDeletedAtIsNull(hubId)
                .orElseThrow(() -> AppException.of(HubErrorCode.HUB_NOT_FOUND));
    }


    @Override
    public Page<Hub> getHubPage(String keyword, Pageable pageable){
        if (keyword != null && !keyword.isEmpty()) {
            return hubRepository.searchHubs(keyword, pageable);
        }

        return hubRepository.findAllActive(pageable);
    }

}

