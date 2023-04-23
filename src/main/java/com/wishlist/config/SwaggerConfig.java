package com.wishlist.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Wishlist API")
						.description("API para controle de uma Wishlist de e-commerce")
						.version("0.0.1")
						.license(new License().name("Jean Rafael Gonçalves").url("https://github.com/jeanrgoncalves/wishlist")));
	}

}