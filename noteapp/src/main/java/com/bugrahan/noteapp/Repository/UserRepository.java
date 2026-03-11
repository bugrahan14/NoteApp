package com.bugrahan.noteapp.Repository;

import com.bugrahan.noteapp.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    /**
     * Giriş (login) sırasında kullanıcıyı kullanıcı adına göre bulmak için.
     * Spring Security UserDetailsService bu metodu kullanır.
     */
    Optional<Users> findByUsername(String username);
}
