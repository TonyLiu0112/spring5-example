package com.tony666.demo.spring5.routers;

import com.tony666.demo.spring5.handlers.ErrorHandler;
import com.tony666.demo.spring5.handlers.MessageHandler;
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
public class MessageRouter {

    private final MessageHandler messageHandler;
    private final ErrorHandler errorHandler;

    public MessageRouter(MessageHandler messageHandler, ErrorHandler errorHandler) {
        this.messageHandler = messageHandler;
        this.errorHandler = errorHandler;
    }

    @Bean
    public RouterFunction<?> setMessageRouter(RouterFunction<?> routerFunction) {
        return routerFunction.andOther(
                nest(path("/msg"),
                        nest(accept(APPLICATION_JSON),
                                route(GET("/message"), messageHandler::getMessage)
                                        .andRoute(POST("/message"), messageHandler::postMessage)
                        ).andOther(route(all(), errorHandler::notFund))
                )
        );
    }

}
