package com.logilink.hub.domain.hubroute.service;

import com.logilink.hub.domain.hub.model.entity.Hub;
import com.logilink.hub.domain.hub.repository.HubRepository;
import com.logilink.hub.domain.hubroute.model.entity.HubRoute;
import com.logilink.hub.domain.hubroute.repository.HubRouteRepository;
import com.sparta.logilinkcommon.common.exception.AppException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.logilink.hub.common.exception.HubErrorCode.HUB_NOT_FOUND;
import static com.logilink.hub.common.exception.HubErrorCode.HUB_ROUTE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubRouteAlgorithmService {

    private final HubRepository hubRepository;
    private final HubRouteRepository hubRouteRepository;

    @Getter
    @AllArgsConstructor
    private static class HubEdge {
        private final UUID destinationHubId;
        private final double distance;
    }

    @Getter
    @AllArgsConstructor
    private static class Node implements Comparable<Node> {
        private final UUID hubId;
        private final double distance;

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.distance, other.distance);
        }
    }

    // 다익스트라 알고리즘 - 허브 간 최단 경로 게산
    @Transactional(readOnly = true)
    public List<Hub> findShortestPath(UUID originHubId, UUID destinationHubId) {
        Hub origin = hubRepository.findByIdAndDeletedAtIsNull(originHubId)
                .orElseThrow(() -> AppException.of(HUB_NOT_FOUND));
        Hub destination = hubRepository.findByIdAndDeletedAtIsNull(destinationHubId)
                .orElseThrow(() -> AppException.of(HUB_NOT_FOUND));

        // 그래프 구성
        Map<UUID, List<HubEdge>> graph = buildGraph();
        if (graph.isEmpty()) {
            throw AppException.of(HUB_ROUTE_NOT_FOUND);
        }

        // 거리 및 이전 허브 저장용 구조
        Map<UUID, Double> distance = new HashMap<>();
        Map<UUID, UUID> previous = new HashMap<>();
        Set<UUID> visited = new HashSet<>();

        // 초기화
        for (UUID hubId : graph.keySet()) {
            distance.put(hubId, Double.POSITIVE_INFINITY);
        }
        distance.put(originHubId, 0.0);

        // 우선순위 큐
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(originHubId, 0.0));

        // 다익스트라
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            UUID current = currentNode.getHubId();

            if (!visited.add(current)) continue;
            if (current.equals(destinationHubId)) break;

            for (HubEdge edge : graph.getOrDefault(current, Collections.emptyList())) {
                double newDist = distance.get(current) + edge.getDistance();
                UUID nextHubId = edge.getDestinationHubId();

                if (newDist < distance.get(nextHubId)) {
                    distance.put(nextHubId, newDist);
                    previous.put(nextHubId, current);
                    queue.add(new Node(nextHubId, newDist));
                }
            }
        }


        // 경로 복원
        List<Hub> path = reconstructPath(originHubId, destinationHubId, previous);
        if (path.isEmpty()) {
            throw AppException.of(HUB_ROUTE_NOT_FOUND);
        }

        return path;
    }

    // 전체 허브-경로 관계를 그래프로 구성
    @Cacheable(value = "hubRoutes", key = "'allGraph'")
    public Map<UUID, List<HubEdge>> buildGraph() {

        Map<UUID, List<HubEdge>> graph = new HashMap<>();
        List<HubRoute> routes = hubRouteRepository.findAllActiveList();

        for (HubRoute route : routes) {
            UUID originId = route.getOriginHub().getId();
            UUID destId = route.getDestinationHub().getId();
            double distance = route.getTotalDistance();

            graph.computeIfAbsent(originId, k -> new ArrayList<>())
                    .add(new HubEdge(destId, distance));
        }

        return graph;
    }

    // 경로 복원 (destination부터 역추적)
    private List<Hub> reconstructPath(UUID originId, UUID destId, Map<UUID, UUID> previous) {
        LinkedList<Hub> path = new LinkedList<>();
        UUID current = destId;

        while (current != null) {
            Hub hub = hubRepository.findByIdAndDeletedAtIsNull(current).orElse(null);
            if (hub != null) path.addFirst(hub);
            current = previous.get(current);
        }

        // 출발지 포함 여부 확인
        if (!path.isEmpty() && path.getFirst().getId().equals(originId)) {
            return path;
        }
        return Collections.emptyList();
    }

}
