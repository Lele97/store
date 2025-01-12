package com.webapp.enterprice.spring.api_gateway_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GatewayConfig {

//    @Autowired
//    RequestFilter requestFilter;
//
//    @Autowired
//    AuthFilter authFilter;
//
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//
//        //TODO add all route
//
//        // adding 2 rotes to first microservice as we need to log request body if method is POST
//        return builder.routes()
//                .route("product-service", r -> r.path("/api/v1/products")
//                        .and().method("POST").filters(f -> f.filters(requestFilter, authFilter))
//                        .uri("http://localhost:9093"))
//                .route("product-service", r -> r.path("/api/v1/products")
//                        .and().method("GET").filters(f -> f.filters(authFilter))
//                        .uri("http://localhost:9093"))
////                .route("second-microservice",r -> r.path("/second")
////                        .and().method("POST")
////                        .and().readBody(Company.class, s -> true).filters(f -> f.filters(requestFilter, authFilter))
////                        .uri("http://localhost:8082"))
////                .route("second-microservice",r -> r.path("/second")
////                        .and().method("GET").filters(f-> f.filters(authFilter))
////                        .uri("http://localhost:8082"))
//                .route("auth-server", r -> r.path("/login")
//                        .uri("http://localhost:8088"))
//                .build();
//    }


    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public WebFilter responseFilter() {
//        return new PostGlobalFilter();
//    }

}
