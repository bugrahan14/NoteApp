package com.bugrahan.noteapp.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security yapılandırması.
 * <p>
 * Clean code: Her bean tek bir sorumluluk taşır; path'ler {@link SecurityConstants} üzerinden okunur.
 */
@Configuration
@EnableWebSecurity
public class DemoSecurity {

    /**
     * Şifreleri hash'lemek için BCrypt kullanılır.
     * Kayıt sırasında ve giriş doğrulamasında aynı encoder kullanılmalıdır.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Hangi URL'lerin herkese açık, hangilerinin giriş gerektirdiği burada tanımlanır.
     * - POST /users: Kayıt için herkese açık.
     * - Diğer tüm istekler: Kimlik doğrulama gerekir.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll()
                        .requestMatchers(SecurityConstants.NOTES_PATH).authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                );

        return http.build();
    }

    /**
     * Form login ve giriş doğrulaması için. Context'teki UserDetailsService ve PasswordEncoder otomatik kullanılır.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
