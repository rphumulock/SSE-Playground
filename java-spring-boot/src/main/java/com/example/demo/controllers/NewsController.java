package com.example.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class NewsController {

    public List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @CrossOrigin
    @RequestMapping(value = "/subscribe", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        try {
            emitter.send(SseEmitter.event().name("INIT").data("Welcome to the news feed!"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitters.add(emitter);
        return emitter;
    }

    @PostMapping(value = "/dispatchEvent")
    public void dispatchEvent(@RequestParam String message) {
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event().name("NEWS").data(message));
            } catch (IOException e) {
                emitters.remove(emitter);
                e.printStackTrace();
            }
        });
    }

}
