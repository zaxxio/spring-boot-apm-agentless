package org.wsd.app.payload;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Payload<T> {
    private final String message;
    private final int status;
    private final T payload;
    private final Date timestamp;

    private Payload(PayloadStatus status, String message, T payload) {
        this.status = status.getCode();
        this.message = message;
        this.payload = payload;
        this.timestamp = Date.from(Instant.now()
                .atZone(ZoneId.of("UTC"))
                .toInstant());
    }

    public static class Builder<T> {
        private String message;
        private PayloadStatus status;
        private T payload;

        public Builder<T> message(String message) {
            this.message = message.toUpperCase();
            return this;
        }

        public Builder<T> status(PayloadStatus status) {
            this.status = status;
            return this;
        }

        public Builder<T> payload(T payload) {
            this.payload = payload;
            return this;
        }

        public Payload<T> build() {
            return new Payload<>(status, message, payload);
        }
    }
}