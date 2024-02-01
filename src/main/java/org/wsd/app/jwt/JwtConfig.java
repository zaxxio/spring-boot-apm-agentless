package org.wsd.app.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Data
@Configuration
@NoArgsConstructor
@ConfigurationProperties(prefix = "auth")
@PropertySource(value = "classpath:jwt.yml")
public class JwtConfig {
    private String jwtSecret;
    private int jwtExpiration;
}

