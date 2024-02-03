package org.wsd.app.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LocationEvent implements Serializable {
    private Double latitude;
    private Double longitude;
    private Instant instant = Instant.now();
}
