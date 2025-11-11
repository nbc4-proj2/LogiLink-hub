package com.logilink.hub.domain.hubroute.repository;

import com.logilink.hub.domain.hub.model.entity.Hub;
import com.logilink.hub.domain.hubroute.model.entity.HubRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubRouteRepository extends JpaRepository<HubRoute, UUID> {

    @Query("""
SELECT r FROM HubRoute r
WHERE r.originHub = :originHub
AND r.deletedAt IS NULL
""")
    List<HubRoute> findActiveByOrigin(@Param("originHub") Hub originHub);

    @Query("""
SELECT r FROM HubRoute r
WHERE r.destinationHub = :destinationHub
AND r.deletedAt IS NULL
""")
    List<HubRoute> findActiveByDestination(@Param("destinationHub") Hub destinationHub);

    @Query("""
SELECT r FROM HubRoute r
WHERE r.originHub = :originHub
AND r.destinationHub = :destinationHub
AND r.deletedAt IS NULL
""")
    Optional<HubRoute> findActiveRoute(@Param("originHub") Hub originHub, @Param("destinationHub") Hub destinationHub);

    @EntityGraph(attributePaths = {"originHub", "destinationHub"})
    @Query("""
SELECT r FROM HubRoute r
WHERE r.deletedAt IS NULL
AND (
LOWER(r.originHub.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(r.destinationHub.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
""")
    Page<HubRoute> searchRoutes(@Param("keyword") String keyword, Pageable pageable);


    @Query("SELECT r FROM HubRoute r WHERE r.deletedAt IS NULL")
    Page<HubRoute> findAllActive(Pageable pageable);

    Optional<HubRoute> findByIdAndDeletedAtIsNull(UUID routeId);

    @Query("SELECT r FROM HubRoute r WHERE r.deletedAt IS NULL")
    List<HubRoute> findAllActiveList();

}

