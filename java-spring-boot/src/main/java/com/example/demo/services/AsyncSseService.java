package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncSseService {

    @Autowired
    TemplateService templateService;

    @Autowired
    private YourEmitterService emitterService; // This service will manage the SseEmitters

    @Async
    public CompletableFuture<SseEmitter.SseEventBuilder> generateEvent(SseEmitter emitter) {
        Map<String, Object> model = new HashMap<>();
        model.put("msg", "YOYO");
        String htmlContent = templateService.renderTemplate("test1", model);

        SseEmitter.SseEventBuilder event = emitter.event()
            .name(" datastar-fragment")
            .id(" " + UUID.randomUUID())
            .data(" selector ")
            .data(" merge morph_element")
            .data(" settle 0")
            .data(" fragment " + htmlContent);
        return CompletableFuture.completedFuture(event);
    }

    @Async
    public CompletableFuture<SseEmitter.SseEventBuilder> generateEvent2(SseEmitter emitter) {
        Map<String, Object> model = new HashMap<>();
        model.put("emitterId", "YOYO");
        String htmlContent = templateService.renderTemplate("test", model);
        SseEmitter.SseEventBuilder event = SseEmitter.event()
                .name(" datastar-fragment")
                .id(" " + UUID.randomUUID())
                .data(" selector ")
                .data(" merge morph_element")
                .data(" settle 0")
                .data(" fragment " + htmlContent);
        return CompletableFuture.completedFuture(event);
    }
}

