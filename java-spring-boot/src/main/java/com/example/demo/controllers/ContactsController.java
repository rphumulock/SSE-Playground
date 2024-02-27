package com.example.demo.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/sse")
public class ContactsController {
    Logger logger = LoggerFactory.getLogger(ContactsController.class);

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @GetMapping(value = "/service-executor", produces = "text/event-stream")
    public SseEmitter serviceexecutor() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        String UID = String.valueOf(UUID.randomUUID());

        executor.execute(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                            .name("message")
                            .id(String.valueOf(UUID.randomUUID()))
                            .data("Message " + i + " of " + UID);
                    emitter.send(event);
                    Thread.sleep(1000); // Delay to simulate real-time messaging
                }
                // Example of sending a final "complete" message
                emitter.send(SseEmitter.event()
                        .id(String.valueOf(UUID.randomUUID()))
                        .name("stream-complete")
                        .data("Stream complete"));

                emitter.complete();
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e);
            }
        });

        emitter.onCompletion(() -> {
            logger.info("SSE MVC - onCompletion");
        });

        emitter.onError((ex) -> {
            logger.info("SSE MVC - onError");
        });

        emitter.onTimeout(() -> {
            logger.info("SSE MVC - onTimeout");
        });

        return emitter;
    }

    @GetMapping(value = "/httpServletResponse", produces = "text/event-stream")
    public void httpServletResponse(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");
        String UID = String.valueOf(UUID.randomUUID());
        try (PrintWriter writer = response.getWriter()) {
            for (int i = 0; i < 10; i++) {
                writer.write("event: /sse/httpServletResponse\n");
                writer.write("id: " + UUID.randomUUID() + "\n");
                writer.write("data: " + "Message " + i + " of " + UID + "\n\n"); // Ensure double newline at the end
                writer.flush(); // Flush the writer to send data immediately
                Thread.sleep(1000); // Sleep between sends
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt(); // Handle interrupted exception
            e.printStackTrace();
        }
    }


    @GetMapping(value = "/thread", produces = "text/event-stream")
    public SseEmitter thread() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        String UID = String.valueOf(UUID.randomUUID());

        new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                            .name("message")
                            .id(String.valueOf(UUID.randomUUID()))
                            .data("Message " + i + " of " + UID);

                    emitter.send(event);
                    Thread.sleep(1000); // Delay to simulate real-time messaging
                }

                // Example of sending a final "complete" message
                emitter.send(SseEmitter.event()
                        .id(String.valueOf(UUID.randomUUID()))
                        .name("stream-complete")
                        .data("Stream complete"));

                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();
        return emitter;
    }

}
