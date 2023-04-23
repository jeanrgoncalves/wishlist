package com.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Value("${api-version}")
	private String apiVersion;

	/*@Bean
	public Docket mainApi() {

		var apiInfo = new ApiInfoBuilder()
				.title("Entregamais API - Via Varejo")
				.description("Spring boot REST API")
				.version(apiVersion)
				.build();

		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("EntregaMais")
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(Arrays.asList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
				.apis(Predicate.not(RequestHandlerSelectors.basePackage(BASE_MAINFRAME_PACKAGE)))
				.build()
				.apiInfo(apiInfo);
	}*/

	@Bean
	public OpenAPI springShopOpenAPI() {
		final String securitySchemeName = "bearerAuth";
		return new OpenAPI()
				.info(new Info().title("Entregamais API - VIA")
						.description("Spring boot REST API")
						.version(apiVersion)
						.license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation()
						.description("SpringShop Wiki Documentation")
						.url("https://springshop.wiki.github.org/docs"))
				.addSecurityItem(new SecurityRequirement()
						.addList(securitySchemeName))
				.components(new Components()
						.addSecuritySchemes(securitySchemeName, new SecurityScheme()
								.name(securitySchemeName)
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")));
	}

	/*private ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "header");
	}

	private SecurityContext securityContext() { 
	    return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
	} 

	private List<SecurityReference> defaultAuth() {
	    var authorizationScope = new AuthorizationScope("global", "accessEverything");
	    var authorizationScopes = new AuthorizationScope[1];
	    authorizationScopes[0] = authorizationScope; 
	    return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
	}*/

}