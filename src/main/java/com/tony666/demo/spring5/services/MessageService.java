package com.tony666.demo.spring5.services;

import com.tony666.demo.spring5.model.MessageRequest;
import com.tony666.demo.spring5.model.MessageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageService {

    public Mono<MessageResponse> getMessage(Mono<String> nameMono) {
        return nameMono.flatMap(name -> Mono.just(new MessageResponse(name, "Hi, " + name + "! Welcome to Spring 5!")));
    }

    public Mono<MessageResponse> saveMessage(Mono<MessageRequest> messageRequestMono) {
        return messageRequestMono.flatMap(messageRequest -> Mono.just(new MessageResponse(messageRequest.getName(), "保存消息成功")));
    }

}
