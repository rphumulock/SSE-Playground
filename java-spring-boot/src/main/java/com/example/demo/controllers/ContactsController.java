package com.example.demo.controllers;

import com.example.demo.services.AsyncSseService;
import com.example.demo.services.TemplateService;
import com.example.demo.services.YourEmitterService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@RestController
@RequestMapping("/contact")
public class ContactsController {
    Logger logger = LoggerFactory.getLogger(ContactsController.class);

    @Autowired
    private YourEmitterService emitterService; // This service will manage the SseEmitters

    @Autowired
    private TemplateService templateService; // This service will render Thymeleaf templates

    @Autowired
    private AsyncSseService asyncSseService;

    @GetMapping("/{id}")
    public void streamEvents(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");
        response.setHeader("Content-Encoding", "br");
//        response.setHeader("Vary", "Accept-Encoding");
//        response.setHeader("Transfer-Encoding", "chunked");

        try (PrintWriter writer = response.getWriter()) {
                // Data format for SSE
                writer.write("event: datastar-fragment" + "\n");
                writer.write("id: " + UUID.randomUUID() + "\n");
                writer.write("data: selector " + "\n");
                writer.write("data: merge morph_element" + "\n");
                writer.write("data: settle 0" + "\n");
                writer.write("data: fragment <button id=\"contact_1\" class=\"btn btn-primary ml-24\" data-fetch-url=\"'/contact/1/dispatch'\" data-on-click=\"$$get\">Send </button>");
                writer.flush(); // Flush the writer to send data immediately
        } catch (IOException e) {
            // Handle IO exception
            logger.info("Error: " + e.getMessage());
        }
    }
//    @GetMapping("/{id}")
//    public SseEmitter startAndSend(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
//        String clientId = request.getSession().getId();
//        SseEmitter emitter;
//        response.setContentType("text/event-stream");
//        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setHeader("Connection", "keep-alive");
//        response.setHeader("Content-Encoding", "br");
//        response.setHeader("Vary", "Accept-Encoding");
//        response.setHeader("Transfer-Encoding", "chunked");
//
//        if (emitterService.hasEmitter(clientId)) {
//            emitter = emitterService.getEmitter(clientId);
//        } else {
//            emitter = emitterService.createEmitter(clientId);
//        }
//        Map<String, Object> model = new HashMap<>();
//        model.put("msg", "YOYO");
//        String htmlContent = templateService.renderTemplate("test1", model);
//
//        SseEmitter.SseEventBuilder event = emitter.event()
//                .name(" datastar-fragment")
//                .id(" " + UUID.randomUUID())
//                .data(" selector ")
//                .data(" merge morph_element")
//                .data(" settle 0")
//                .data(" fragment " + htmlContent);
//
//            try {
//                emitter.send(event);
//            } catch (IOException e) {
//                emitter.completeWithError(e);
//            }
//
//        emitter.onCompletion(() -> {
//            logger.info("SSE MVC - onCompletion");
//        });
//        return emitter;
//    }

    @GetMapping("/{id}/dispatch")
    public void dispatch(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");
//        response.setHeader("Content-Encoding", "br");
//        response.setHeader("Vary", "Accept-Encoding");
//        response.setHeader("Transfer-Encoding", "chunked");

        try (PrintWriter writer = response.getWriter()) {
            // Data format for SSE
            writer.write("event: datastar-fragment" + "\n");
            writer.write("id: " + UUID.randomUUID() + "\n");
            writer.write("data: selector " + "\n");
            writer.write("data: merge morph_element" + "\n");
            writer.write("data: settle 0" + "\n");
            writer.write("data: fragment <button id=\"contact_1\" class=\"btn btn-primary ml-24\" data-fetch-url=\"'/contact/1'\" data-on-click=\"$$get\">Send Again</button>");
            writer.flush(); // Flush the writer to send data immediately
        } catch (IOException e) {
            // Handle IO exception
        }
    }

//    @GetMapping("/{id}/dispatch")
//    public SseEmitter dispatchEvent(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
//        String clientId = request.getSession().getId();
//        SseEmitter emitter;
//        response.setContentType("text/event-stream");
//        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setHeader("Connection", "keep-alive");
//        response.setHeader("Content-Encoding", "br");
//        response.setHeader("Vary", "Accept-Encoding");
//        response.setHeader("Transfer-Encoding", "chunked");
//
//        if (emitterService.hasEmitter(clientId)) {
//            emitter = emitterService.getEmitter(clientId);
//        } else {
//            emitter = emitterService.createEmitter(clientId);
//        }
//
//        Map<String, Object> model = new HashMap<>();
//        model.put("emitterId", "YOYO");
//        String htmlContent = templateService.renderTemplate("test", model);
//        SseEmitter.SseEventBuilder event = SseEmitter.event()
//                .name(" datastar-fragment")
//                .id(" " + UUID.randomUUID())
//                .data(" selector ")
//                .data(" merge morph_element")
//                .data(" settle 0")
//                .data(" fragment " + htmlContent);
//
//            try {
//                emitter.send(event);
//            } catch (IOException e) {
//                emitter.completeWithError(e);
//            }
//
//
//        emitter.onCompletion(() -> {
//            logger.info("SSE MVC - onCompletion");
//        });
//
//        return emitter;
//    }

//
//    @PostMapping("/{id}/dispatch")
//    public SseEmitter dispatch() {
//        SseEmitter emitter = new SseEmitter();
//        ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
//        sseMvcExecutor.execute(() -> {
//            try {
//                SseEmitter.SseEventBuilder event = SseEmitter.event()
//                        .name(" datastar-fragment")
//                        .id(" " + UUID.randomUUID())
//                        .data(" selector ")
//                        .data(" merge morph_element")
//                        .data(" settle 0")
//                        .data(" fragment <button id=\"contact_1\" class=\"btn btn-primary ml-24\" data-fetch-url=\"'/contact/1'\" data-on-click=\"$$get\">Send Again</button>");
//
//                emitter.send(event);
//                emitter.complete();
//            } catch (Exception ex) {
//                emitter.completeWithError(ex);
//            }
//        });
//    }

}
//
//        emitter.onCompletion(() -> {
//            logger.info("SSE MVC - onCompletion");
//            sseMvcExecutor.shutdown();
//        });
//
//        return emitter;
//    }

//    @PostMapping("/{id}/dispatch")
//    public SseEmitter dispatchEvent(@PathVariable String id, HttpServletRequest request) {
//        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
//        asyncSseService.generateEvent2().thenAcceptAsync(event -> {
//            try {
//                emitter.send(event);
//                emitter.complete();
//            } catch (IOException e) {
//                emitter.completeWithError(e);
//            }
//        }).exceptionally(e -> {
//            emitter.completeWithError(e);
//            return null;
//        });
//        return emitter;
//    }

//    public SseEmitter doit() {
//        SseEmitter emitter = new SseEmitter();
//            try {
//                SseEmitter.SseEventBuilder event = SseEmitter.event()
//                        .name(" datastar-fragment")
//                        .id(" " + UUID.randomUUID())
//                        .data(" selector ")
//                        .data(" merge morph_element")
//                        .data(" settle 0")
//                        .data(" fragment <button id=\"contact_1\" class=\"btn btn-primary ml-52\" data-fetch-url=\"'/contact/1/dispatch'\" data-on-click=\"$$post\">Dispatch</button>");
//
//                emitter.send(event);
//                emitter.complete();
//            } catch (Exception ex) {
//                emitter.completeWithError(ex);
//            }
//
//        emitter.onCompletion(() -> {
//            logger.info("SSE MVC - onCompletion");
//        });
//
//        return emitter;
//    }
//
//
//    @GetMapping("/{id}")
//    public SseEmitter streamSseMvc() {
//        SseEmitter emitter = new SseEmitter();
//        ExecutorService executor = Executors.newFixedThreadPool(4); // N is the number of threads
//
//        try {
//            this.doit();
//        } finally {
//            executor.shutdown(); // Disable new tasks from being submitted
//            try {
//                // Wait a while for existing tasks to terminate
//                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
//                    executor.shutdownNow(); // Cancel currently executing tasks
//
//                    // Wait a while for tasks to respond to being cancelled
//                    if (!executor.awaitTermination(60, TimeUnit.SECONDS))
//                        System.err.println("ExecutorService did not terminate");
//                }
//            } catch (InterruptedException ie) {
//                // (Re-)Cancel if current thread also interrupted
//                executor.shutdownNow();
//
//                // Preserve interrupt status
//                Thread.currentThread().interrupt();
//            }
//        }
//        return emitter;
//    }
//
//    @GetMapping("/{id}/dispatch")
//    public SseEmitter dispatchEvent(@PathVariable String id, HttpServletRequest request) {
//        final SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
//        executor.submit(() -> {
//                try {
//                    SseEmitter.SseEventBuilder event = SseEmitter.event()
//                            .name(" datastar-fragment")
//                            .id(" " + UUID.randomUUID())
//                            .data(" selector ")
//                            .data(" merge morph_element")
//                            .data(" settle 0")
//                            .data(" fragment <button id=\"contact_1\" class=\"btn btn-primary ml-24\" data-fetch-url=\"'/contact/1'\" data-on-click=\"$$get\">Send Again</button>")
//                            .reconnectTime(50);
//
//                    emitter.send(event);
//                    emitter.complete();
//                } catch (Exception ex) {
//                    emitter.completeWithError(ex);
//                }
//            });
//
//            emitter.onCompletion(() -> {
//                System.out.println("SSE MVC - onCompletion");
//            });
//            emitter.onTimeout(() -> System.out.println("SSE MVC - onTimeout"));
//            emitter.onError((ex) -> System.out.println("SSE MVC - onError"));
//
//            return emitter;
//    }
//
//    @GetMapping("/{id}")
//    public SseEmitter streamSseMvc() {
//        final SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
//        executor.submit(() -> {
//            try {
//                SseEmitter.SseEventBuilder event = SseEmitter.event()
//                        .name(" datastar-fragment")
//                        .id(" " + UUID.randomUUID())
//                        .data(" selector ")
//                        .data(" merge morph_element")
//                        .data(" settle 0")
//                        .data(" fragment <button id=\"contact_1\" class=\"btn btn-primary ml-52\" data-fetch-url=\"'/contact/1/dispatch'\" data-on-click=\"$$post\">Dispatch</button>")
//                        .reconnectTime(50);
//
//
//                emitter.send(event);
//                emitter.complete();
//            } catch (Exception ex) {
//                emitter.completeWithError(ex);
//            }
//        });
//
//        emitter.onCompletion(() -> {
//            System.out.println("SSE MVC - onCompletion");
//        });
//        emitter.onTimeout(() -> System.out.println("SSE MVC - onTimeout"));
//        emitter.onError((ex) -> System.out.println("SSE MVC - onError"));
//
//        return emitter;
//    }
//
//    // Call this method from a @PreDestroy annotated method or similar to ensure proper shutdown
//    public void shutdownExecutor() {
//        try {
//            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
//                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
//                    System.err.println("ExecutorService did not terminate");
//                }
//            }
//        } catch (InterruptedException ie) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
//}
//
//
//
//
//
//


//
//    @GetMapping("/{id}")
//    public SseEmitter startAndSend(@PathVariable String id, HttpServletRequest request) {
////        String clientId = request.getSession().getId();
////        SseEmitter emitter;
//
//
////        logger.info("\n" + "startAndSend" + "\n");
////
////        if (!emitterService.hasEmitter(clientId)) {
////             emitter = emitterService.createEmitter(clientId);
////             logger.info("\n" + "New emitter created for client: " + clientId + "\n");
////        } else {
////             emitter = emitterService.getEmitter(clientId);
////             logger.info("\n" + "Found emitter created for client: " + clientId + "\n");
////        }
//        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
//
//
//
//        new Thread(() -> {
//
//            try {
//                Map<String, Object> model = new HashMap<>();
//                model.put("emitterId", "YOYO");
//                String htmlContent = "<button id=\"contact_1\" class=\"btn btn-primary ml-52\" data-fetch-url=\"'/contact/1/dispatch'\" data-on-click=\"$$post\">Send</button>";
//
//
////                    templateService.renderTemplate("test", model);
//
//
//                SseEmitter.SseEventBuilder event = SseEmitter.event()
//                        .name(" datastar-fragment")
//                        .id(" " + UUID.randomUUID())
//                        .data(" selector ")
//                        .data(" merge morph_element")
//                        .data(" settle 0")
//                        .data(" fragment " + htmlContent)
//                        .reconnectTime(500);
//
//                emitter.send(event);
//                emitter.complete();
//
////            this.emitterService.removeEmitter(clientId);
//            } catch (Exception e) {
//                emitter.completeWithError(e);
//            }
//        }).start();
//        return emitter;
//    }

//    @PostMapping("/{id}/dispatch")
//    public SseEmitter dispatchEvent(@PathVariable String id, HttpServletRequest request) {
//        String clientId = request.getSession().getId();
//        SseEmitter emitter;
//
//        logger.info("\n" + "dispatchEvent" + "\n");
//
//
//        if (!emitterService.hasEmitter(clientId)) {
//            emitter = emitterService.createEmitter(clientId);
//
//            logger.info("\n" + "New emitter created for client: " + clientId + "\n");
//        } else {
//            emitter = emitterService.getEmitter(clientId);
//            logger.info("\n" + "Found emitter created for client: " + clientId + "\n");
//        }
//        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
//
//
//        new Thread(() -> {
//
//            try {
//                Map<String, Object> model = new HashMap<>();
//                model.put("emitterId", "YOYO");
//                String htmlContent = "<button id=\"contact_1\" class=\"btn btn-primary ml-24\" data-fetch-url=\"'/contact/1'\" data-on-click=\"$$get\">Send Again</button>";
//
//                        // templateService.renderTemplate("test1", model);
//                SseEmitter.SseEventBuilder event = SseEmitter.event()
//                        .name(" datastar-fragment")
//                        .id(" " + UUID.randomUUID())
//                        .data(" selector ")
//                        .data(" merge morph_element")
//                        .data(" settle 0")
//                        .data(" fragment " + htmlContent)
//                        .reconnectTime(500);
//                emitter.send(event);
//                emitter.complete();
//            } catch (Exception e) {
//                emitter.completeWithError(e);
//            }
//        }).start();
//        return emitter;
//    }

