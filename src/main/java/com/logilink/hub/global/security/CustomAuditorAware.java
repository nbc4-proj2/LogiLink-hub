package com.logilink.hub.global.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAware")
public class CustomAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of(1L); // 비로그인 상태 테스트용
        }

        // 실제론 JWT payload에서 userId 꺼내서 넣어야 함
        // 예: return Optional.of(jwtService.extractUserId(authentication));
        return Optional.of(999L); // 예시값
    }
}
