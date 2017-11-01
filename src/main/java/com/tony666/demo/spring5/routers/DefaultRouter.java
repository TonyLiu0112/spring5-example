package com.tony666.demo.spring5.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class DefaultRouter {

    @Bean
    public RouterFunction<?> routerFunction() {
        return nest(path("/"),
                nest(accept(MediaType.ALL),
                        route(
                                GET("/default"),
                                request -> ServerResponse.ok().body(Mono.just("Worked!"), String.class)
                        )
                )
        );
    }

}