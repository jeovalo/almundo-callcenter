package com.jeovalo.almundo.callcenter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@javax.annotation.Generated(value = "com.jeovalo.almundo.callcenter.codegen.languages.SpringCodegen", date = "2018-11-19T14:12:57.069Z")

@Configuration
public class SwaggerDocumentationConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Almundo Callcenter")
            .description("Este es un servidor de ejemplo de Almundo Callcenter. Usted puede saber más acerca de Almundo Callcenter     en [http://almundo.callcenter.com](http://almundo.callcenter..com) o en [irc.freenode.net, #almundo-callcenter](http://almundo.callcenter.com/irc/).      Para esta prueba, usted puede usar una api key `special-key` para probar los filtros de authorization.")
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .termsOfServiceUrl("")
            .version("1.0.0")
            .contact(new Contact("","", "apiteam@callcenter.almundo.com"))
            .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.jeovalo.almundo.callcenter.api"))
                    .build()
                .directModelSubstitute(org.threeten.bp.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(org.threeten.bp.OffsetDateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }

}
