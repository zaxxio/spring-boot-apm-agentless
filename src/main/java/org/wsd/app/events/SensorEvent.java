package org.wsd.app.events;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SensorEvent implements Serializable {
    private double x;
    private double y;
    private Instant instant = Instant.now();
}
