package io.eddie.accountsservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationSuccessHandler authenticationSuccessHandler
    ) throws Exception {
        return http

                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())

//                .formLogin(Customizer.withDefaults())
                .formLogin(form -> form.successHandler(authenticationSuccessHandler))

                .sessionManagement(
                        config -> config.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
//                                .requestMatchers(HttpMethod.POST, "/accounts").permitAll()
                                .requestMatchers(HttpMethod.POST, "/accounts/**").permitAll()
                                .anyRequest().permitAll()
                )

                .build();
    }

}
//
