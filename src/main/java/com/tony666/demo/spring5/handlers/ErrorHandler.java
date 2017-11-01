package com.tony666.demo.spring5.handlers;

import com.tony666.demo.spring5.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ErrorHandler {

    public Mono<ServerResponse> notFund(final ServerRequest request) {
        return this.buildError(new Exception("Not fund."));
    }

    public Mono<ServerResponse> buildError(final Throwable throwable) {
        return Mono.just(throwable).transform(throwableMono ->
                throwableMono.flatMap(error ->
                        ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Mono.just(new ErrorResponse(throwable.getMessage())), ErrorResponse.class))
        );
    }

}
