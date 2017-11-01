package com.tony666.demo.spring5.handlers;

import com.tony666.demo.spring5.services.OrderService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class OrderHandler {

    private final OrderService orderService;
    private final ErrorHandler errorHandler;

    public OrderHandler(OrderService orderService, ErrorHandler errorHandler) {
        this.orderService = orderService;
        this.errorHandler = errorHandler;
    }

    /**
     * 获取用户所有订单
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getOrders(final ServerRequest request) {
        return Mono.just(Long.valueOf(request.pathVariable("userId")))
                .transform(this::buildOrderResponse)
                .onErrorResume(errorHandler::buildError);
    }

    private Mono<ServerResponse> buildOrderResponse(Mono<Long> userIdMono) {
        return userIdMono
                .transform(orderService::getOrders)
                .flatMap(orderResponses -> ServerResponse.ok().body(Mono.just(orderResponses), List.class));
    }

}
