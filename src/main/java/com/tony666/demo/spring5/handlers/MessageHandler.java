package com.tony666.demo.spring5.handlers;

import com.tony666.demo.spring5.model.MessageRequest;
import com.tony666.demo.spring5.model.MessageResponse;
import com.tony666.demo.spring5.services.MessageService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class MessageHandler {

    private final MessageService messageService;
    private final ErrorHandler errorHandler;

    public MessageHandler(MessageService messageService, ErrorHandler errorHandler) {
        this.messageService = messageService;
        this.errorHandler = errorHandler;
    }

    /**
     * 获取消息
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getMessage(final ServerRequest request) {
        return Mono.just(request.queryParam("name").orElse("Default"))
                .transform(this::buildResponse)
                .onErrorResume(errorHandler::buildError);
    }

    private Mono<ServerResponse> buildResponse(final Mono<String> nameMono) {
        return nameMono
                .transform(messageService::getMessage)
                .transform(this::serverResponse);
    }

    /**
     * 提交消息
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> postMessage(final ServerRequest request) {
        return request.bodyToMono(MessageRequest.class)
                .transform(messageService::saveMessage)
                .transform(this::serverResponse)
                .onErrorResume(errorHandler::buildError);
    }

    private Mono<ServerResponse> serverResponse(final Mono<MessageResponse> messageResponseMono) {
        return messageResponseMono.flatMap(messageResponse ->
                ServerResponse.ok().body(Mono.just(messageResponse), MessageResponse.class));
    }

}
