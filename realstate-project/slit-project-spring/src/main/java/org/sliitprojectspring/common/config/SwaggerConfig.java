package org.sliitprojectspring.common.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean

    public OpenAPI  RealStateListingAPI( ) {


        return new OpenAPI()
                .info(
                        new Info()
                                .title("Real State Listing API ")
                                .version("v1")
                                .description("Real State lisig api for the project")

                );
    }




}
