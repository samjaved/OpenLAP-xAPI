package de.ude.openlap.xapi.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The SwaggerConfig class is the configuration class for swagger api doc.It is
 * creating API documentation
 * 
 * @author Sammar Javed
 * @version 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
				.select().apis(RequestHandlerSelectors.basePackage("de.ude.openlap.xapi.controller"))
				.paths(regex("/v1.*"))
				.build().apiInfo(metaData()).securitySchemes(Arrays.asList(apiKey()));
	}

	private ApiKey apiKey() {
		return new ApiKey("Authorization", "api_key", "header");
	}

	private ApiInfo metaData() {
		ApiInfo apiInfo = new ApiInfo("Spring Boot REST API", "Spring Boot REST API for OpenLAP-xAPI", "1.0",
				"Terms of service",
				new Contact("Sammar Javed", "https://github.com/samjaved", "sammar.javed@stud.uni-due.de"),
				"Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0");
		return apiInfo;
    }

}
