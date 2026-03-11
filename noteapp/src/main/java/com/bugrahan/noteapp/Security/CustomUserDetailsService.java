package com.bugrahan.noteapp.Security;

import com.bugrahan.noteapp.Entity.Users;
import com.bugrahan.noteapp.Repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Uygulamamızdaki {@code users} tablosunu kullanarak giriş (login) doğrulaması yapar.
 * Spring Security, giriş denemelerinde kullanıcıyı bu servis üzerinden yükler.
 * <p>
 * Clean code: Tek sorumluluk — sadece kullanıcı adına göre UserDetails döndürme.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + username));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(toGrantedAuthorities(user.getRole()))
                .build();
    }

    /**
     * Veritabanındaki rol (örn. "USER") Spring Security yetkisine (örn. "ROLE_USER") dönüştürülür.
     */
    private List<SimpleGrantedAuthority> toGrantedAuthorities(String role) {
        if (role == null || role.isBlank()) {
            return Collections.singletonList(new SimpleGrantedAuthority(SecurityConstants.ROLE_PREFIX + "USER"));
        }
        String authority = role.startsWith(SecurityConstants.ROLE_PREFIX) ? role : SecurityConstants.ROLE_PREFIX + role;
        return List.of(new SimpleGrantedAuthority(authority));
    }
}
