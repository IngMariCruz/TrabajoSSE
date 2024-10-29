package mcg.sse.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SseController {
    private final List<SseEmitter> emitters = new ArrayList<>();

    @GetMapping("/sse")
    public SseEmitter handleSse() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    public void sendEvents() {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send("Evento: " + System.currentTimeMillis(), MediaType.TEXT_EVENT_STREAM);
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }
}
