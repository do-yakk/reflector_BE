package com.doyak.reflector.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	
	private final BuildProperties buildProperties;
	private final GitProperties gitProperties;
	
	public SwaggerConfig(@Autowired(required = false) BuildProperties buildProperties, 
						 @Autowired(required = false) GitProperties gitProperties) {
		this.buildProperties = buildProperties;
		this.gitProperties = gitProperties;
	}

	@Bean
	public OpenAPI customOpenAPI() {
		// JWT Security 설정 
		SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .description("JWT 인증을 위한 Bearer 토큰을 입력하세요!");
		
		SecurityRequirement securityRequirement = new SecurityRequirement().addList("BearerAuth");
		
		// Info 설정
		String version = buildProperties != null ? buildProperties.getVersion() : "N/A";
		Info info = new Info()
				.title("Reflector API")
				.description(buildDescription())
				.version(version);
		return new OpenAPI()
				.components(new Components()
						.addSecuritySchemes("BearerAuth", securityScheme))
				.info(info)
				.addSecurityItem(securityRequirement);
	}
	
	private String buildDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append("Reflector 서비스 API 문서입니다!\n\n");
		if (buildProperties != null) {
			sb.append("- Version: ").append(buildProperties.getVersion()).append("\n")
			  .append("- Build Time: ").append(buildProperties.getTime()).append("\n");	
		}
        if (gitProperties != null) {
            sb.append("- Commit: ").append(gitProperties.getCommitId()).append("\n")
              .append("- Branch: ").append(gitProperties.getBranch()).append("\n");
        }
        return sb.toString();
	}
	
}
