package com.tony666.demo.spring5.services;

import com.tony666.demo.spring5.model.OrderResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderService {

    public Mono<List<OrderResponse>> getOrders(Mono<Long> userIdMono) {
        return userIdMono.flatMap(userId -> Mono.just(mockOrders(userId)));
    }

    private List<OrderResponse> mockOrders(Long userId) {
        return Stream.of(
                new OrderResponse(userId + "订单1", "111"),
                new OrderResponse(userId + "订单2", "222"),
                new OrderResponse(userId + "订单3", "333")
        ).collect(Collectors.toList());
    }
}
