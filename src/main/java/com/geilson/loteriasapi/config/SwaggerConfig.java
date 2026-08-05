package com.geilson.loteriasapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI loteriasOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Loterias API REST")
                        .description("API gratuita de resultados dos jogos das Loterias CAIXA.")
                        .version("2.0.0")
                        .contact(new Contact()
                                .name("Geilson Freire")
                                .url("https://github.com/geilsonfreire/back-end-loterias-api-main")
                                .email("www.geilsonfreireleite@gmail.com")
                        )
                        .license(new License()
                                .name("Apache License Version 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
