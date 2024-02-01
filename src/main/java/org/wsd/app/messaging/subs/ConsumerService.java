package org.wsd.app.messaging.subs;

import lombok.extern.java.Log;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.wsd.app.events.LocationEvent;

@Log
@Service
public class ConsumerService {

    @KafkaListener(topics = "user-location", groupId = "user-group")
    public void consume(@Payload LocationEvent locationEvent) {
        log.info("Location : " + locationEvent.toString());
    }

}
