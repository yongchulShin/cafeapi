package com.medicalip.cafeapi.domains.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	  @Bean
	  public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
	    Info info = new Info()
	        .title("사내카페 API")
	        .version(springdocVersion)
	        .description("사내카페 App의 API")
	        ;

	    return new OpenAPI()
	        .components(new Components()
	        		.addHeaders("ACCESS_TOKEN", null)
//	        		.addSecuritySchemes("bearer-key",
//	        		          new SecurityScheme().type(SecurityScheme.Type.HTTP). scheme("bearer").bearerFormat("JWT"))
	        		)
	        
	        .info(info);
	  }
	
}
