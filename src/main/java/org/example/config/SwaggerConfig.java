package org.example.config;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration  // 이 클래스가 Spring의 설정 클래스임을 나타낸다.
@EnableSwagger2 // Swagger2를 활성화 하는 어노테이션이다.
@EnableWebMvc   // Spring MVC를 활성화 하는 어노테이션이다.
public class SwaggerConfig {

    // API 문서 정보를 생성하는 메서드이다.
    // ( API문서의 제목, 설명, 라이선스 등을 설정한다. )
    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("SpringFramework DeviceAPI 연계 서비스( Hybrid App )")
                .description("스프링 프레임워크 하이브리드앱 실행 환경 - iOS / Android 하이브리드앱 RestAPI 서비스")
                .termsOfServiceUrl("https://saakmiso.tistory.com/106")
                .contact(new Contact("사악미소", "https://saakmiso.tistory.com/", "wicked@saakmiso.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .version("0.0.1")
                .build();
    }

    // 전체 Swagger API 문서 페이지에 대하여 문서화 한다.
    @Bean
    public Docket apiAllService() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("00. 전체 API REST Service")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build();
    }

    // 사용자 접속에 대한 Docket 빈을 생성
    @Bean
    public Docket accessService() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("01. 사용자 접속 RestAPI 서비스")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)   // false로 설정하면
                .host("localhost:8193") // 기본 URL을 명시적으로 지정합니다.
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.example.api.controller")) // API 패키지 이름의 최상위 패키지를 입력하세요
                .paths(PathSelectors.ant("/access/**"))
                .build();
    }

    // 사용자 접속에 대한 Docket 빈을 생성
    // ( JWT 토큰 인증을 필요로 한다. )
    @Bean
    public Docket swaggerService() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("02. Swagger RestAPI 서비스 샘플")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)   // false로 설정하면
                .host("localhost:8193") // 기본 URL을 명시적으로 지정합니다.
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.example.api.controller")) // API 패키지 이름의 최상위 패키지를 입력하세요
                .paths(PathSelectors.ant("/swagger/**"))
                .build()
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()));
    }

    // API 보안에 사용되는 ApiKey 객체를 생성한다.
    private ApiKey apiKey() {

        // "Bearer" 토큰을 헤더의 "Authorization" 필드로 사용한다.
        return new ApiKey("Bearer", "Authorization", "header");
    }

    //  보안 컨텍스트를 생성합니다. 이 컨텍스트는 보안 관련 설정을 담고 있습니다.
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    // 기본 보안 설정을 생성합니다.( Bearer 토큰을 사용 )
    private List<SecurityReference> defaultAuth() {

        // global" 범위의 권한으로 "accessEverything"을 지정한다.
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Collections.singletonList(new SecurityReference("Bearer", authorizationScopes));
    }
}