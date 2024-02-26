package com.example.demo.services;// Import statements remain as they are, assuming you have the necessary imports for Thymeleaf and Spring MVC

import com.example.demo.controllers.ContactsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class YourEmitterService {
    Logger logger = LoggerFactory.getLogger(ContactsController.class);


    
    // Assuming emitters is a thread-safe collection to manage your SseEmitters
    private final ConcurrentMap<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter createEmitter(String clientId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(UUID.randomUUID().toString(), emitter);
        return emitter;
    }

    public SseEmitter getEmitter(String emitterId) {
        return emitters.get(emitterId);
    }

    public SseEmitter removeEmitter(String emitterId) {
        logger.info("\n" + "Emitter removed for client: " + emitterId + "\n");

        return emitters.remove(emitterId);
    }

    public boolean hasEmitter(String emitterId) {
        return emitters.containsKey(emitterId);
    }

}
