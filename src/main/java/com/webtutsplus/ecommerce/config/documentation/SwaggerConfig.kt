package com.webtutsplus.ecommerce.config.documentation

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun productApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.webtutsplus.ecommerce"))
            .paths(PathSelectors.any())
            .build()
    }

    private val apiInfo: ApiInfo
        private get() {
            val contact = Contact("webtutsplus", "http://webtutsplus.com", "contact.webtutsplus@gmail.com")
            return ApiInfoBuilder()
                .title("Ecommerce API")
                .description("Documentation Ecommerce api")
                .version("1.0.0")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .contact(contact)
                .build()
        }
}