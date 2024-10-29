package mcg.sse.service;

import mcg.sse.controller.SseController;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SseService {
    private  SseController sseController;

    public SseService(SseController sseController) {
        this.sseController = sseController;
    }

    @Scheduled(fixedRate = 1000) // Enviar cada segundo
    public void sendEvents() {
        sseController.sendEvents();
    }
}
