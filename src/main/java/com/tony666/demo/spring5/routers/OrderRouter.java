package com.tony666.demo.spring5.routers;

import com.tony666.demo.spring5.handlers.ErrorHandler;
import com.tony666.demo.spring5.handlers.OrderHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
@ConditionalOnBean(DefaultRouter.class)
public class OrderRouter {

    private final OrderHandler orderHandler;
    private final ErrorHandler errorHandler;

    public OrderRouter(OrderHandler orderHandler, ErrorHandler errorHandler) {
        this.orderHandler = orderHandler;
        this.errorHandler = errorHandler;
    }

    @Bean
    public RouterFunction<?> setOrderRouter(RouterFunction<?> routerFunction) {
        return routerFunction.andOther(
                nest(path("/order"),
                        nest(accept(APPLICATION_JSON),
                                route(GET("/orders/{userId}"), orderHandler::getOrders)
                        ).andOther(route(all(), errorHandler::notFund))
                )
        );
    }

}
