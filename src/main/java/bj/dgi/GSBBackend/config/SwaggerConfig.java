package bj.dgi.GSBBackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("bj.dgi.GSBBackend.controller")).build().apiInfo(this.getApiInfo())
                .securitySchemes(this.securitySchemes())
                .securityContexts(Arrays.asList(this.securityContext()));

    }

    public SecurityContext securityContext() {
        AuthorizationScope[] scopes = { new AuthorizationScope("read", "for read operation"),
                new AuthorizationScope("write", "for write operation") };
        List<SecurityReference> securityReferences = Arrays.asList(new SecurityReference("basicAuth", scopes),
                new SecurityReference("Key", scopes), new SecurityReference("User Authentification Token", scopes));
        return SecurityContext.builder().securityReferences(securityReferences)
                .forPaths(PathSelectors.any())
                .build();
    }

    public List<SecurityScheme> securitySchemes() {
        // SecurityScheme basicAuth = new BasicAuth("basicAuth");
        SecurityScheme userAuthToken = new ApiKey("User Authentification Token", "Authorization", "header");
        //  SecurityScheme keyAuth = new ApiKey("Key", "Key", "header");
        return Arrays.asList(
                // keyAuth,
                userAuthToken
                //, basicAuth
        );
    }



    private ApiInfo getApiInfo() {
        Contact contact = new Contact("GSB BACKEND  PROJECT", "https://impots.bj", "contact@finances.bj");
        return new ApiInfoBuilder()
                .title("REST API POUR LA GESTION DES SITES ET BATIMENTS DE LA DGI")
                .description("This page show all ressources that you could handle to communicate with database via REST API.")
                .version("1.0")
                .license("grh v.1.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .contact(contact)
                .build();
    }


//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.OAS_30)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("bj.impots.dgr.grh.controller")).build().apiInfo(this.getApiInfo())
//                .securitySchemes(this.securitySchemes())
//                .securityContexts(Arrays.asList(this.securityContext()));
//
//    }
//
//    public SecurityContext securityContext() {
//        AuthorizationScope[] scopes = { new AuthorizationScope("read", "for read operation"),
//                new AuthorizationScope("write", "for write operation") };
//        List<SecurityReference> securityReferences = Arrays.asList(new SecurityReference("basicAuth", scopes),
//                new SecurityReference("Key", scopes), new SecurityReference("User Authentification Token", scopes));
//        return SecurityContext.builder().securityReferences(securityReferences)
//                .forPaths(PathSelectors.any())
//                .build();
//    }
//
//    public List<SecurityScheme> securitySchemes() {
//        // SecurityScheme basicAuth = new BasicAuth("basicAuth");
//        SecurityScheme userAuthToken = new ApiKey("User Authentification Token", "Authorization", "header");
//        //  SecurityScheme keyAuth = new ApiKey("Key", "Key", "header");
//        return Arrays.asList(
//                // keyAuth,
//                userAuthToken
//                //, basicAuth
//        );
//    }
//
//
//    private ApiInfo getApiInfo() {
//        Contact contact = new Contact("GRH BACKEND  PROJECT", "https://impots.bj", "contact@finances.bj");
//        return new ApiInfoBuilder()
//                .title("REST API POUR LA GESTION DES RESSOURCES HUMAINES DE LA DGI")
//                .description("This page show all ressources that you could handle to communicate with database via REST API.")
//                .version("1.0")
//                .license("grh v.1.0")
//                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
//                .contact(contact)
//                .build();
//    }

}
