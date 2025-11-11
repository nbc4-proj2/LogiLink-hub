package com.logilink.hub.domain.hub.repository;

import com.logilink.hub.domain.hub.model.entity.Hub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubRepository extends JpaRepository<Hub, UUID> {

    // 허브 이름 중복 검사
    boolean existsByName(String name);

    // 삭제 안 된 허브 단건 조회
    Optional<Hub> findByIdAndDeletedAtIsNull(UUID hubId);

    // 검색/페이징/정렬
    @Query("""
SELECT h
FROM Hub h
WHERE h.deletedAt IS NULL
AND (
LOWER(h.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(h.address) LIKE LOWER(CONCAT('%', :keyword, '%')) 
)
""")
    Page<Hub> searchHubs(@Param("keyword") String keyword, Pageable pageable);

    // 키워드 없이 전체 조회 (deletedAt null들만)
    @Query("SELECT h FROM Hub h WHERE h.deletedAt IS NULL")
    Page<Hub> findAllActive(Pageable pageable);

}
