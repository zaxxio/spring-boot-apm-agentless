package org.wsd.app.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(proxyTargetClass = true)
@SecurityScheme(name = "BEARER_TOKEN", scheme = "Bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class SecurityConfig {

    public static String[] PUBLIC_URLS = {
            "/v1/wsd/**",
            "/v1/spring-boot-app/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };

    @Value("${http.port}")
    private int httpPort;
    @Value("${server.port}")
    private int redirectToHttpsPort;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.httpStrictTransportSecurity(hsts -> hsts.includeSubDomains(true)))
                .requestCache(RequestCacheConfigurer::disable)
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(PUBLIC_URLS).permitAll()
                                .requestMatchers("/actuator/**").hasRole("ACTUATOR_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/auth/authenticate").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/auth/authenticate").permitAll()
                                .anyRequest().authenticated()
                )
                .headers(config -> {
                    config.frameOptions(
                            HeadersConfigurer.FrameOptionsConfig::disable
                    );
                })
                .exceptionHandling(exceptionHandler -> {
                    exceptionHandler.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
                    exceptionHandler.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
                })
                .portMapper(portMapperConfig -> {
                    portMapperConfig.http(httpPort).mapsTo(redirectToHttpsPort);
                })
                .requiresChannel(channelRequestMatcherRegistry -> {
                    channelRequestMatcherRegistry.anyRequest().requiresSecure();
                })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.withUsername("user")
                .password(encoder.encode("password"))
                .roles("ACTUATOR_ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}
