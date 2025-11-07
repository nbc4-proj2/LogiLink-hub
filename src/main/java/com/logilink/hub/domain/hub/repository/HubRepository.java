package com.logilink.hub.domain.hub.repository;

import com.logilink.hub.domain.hub.model.entity.Hub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubRepository extends JpaRepository<Hub, UUID> {

    // 삭제 안 된 허브만 조회
    List<Hub> findAllByDeletedAtIsNull();

    // 허브 이름 중복 검사
    boolean existsByName(String name);

    // 삭제 안 된 허브 단건 조회
    Optional<Hub> findByIdAndDeletedAtIsNull(UUID hubId);

}