package com.logilink.hub.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("LogiLink Hub Service API")
                        .description("물류 시스템 허브 서비스의 API 명세서")
                        .version("v1.0.0"))
                .servers(List.of(
                        new Server().url("http://localhost:19096").description("Local Server")
                ));
    }
}
