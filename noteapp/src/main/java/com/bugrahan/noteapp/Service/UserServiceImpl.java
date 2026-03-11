package com.bugrahan.noteapp.Service;

import com.bugrahan.noteapp.Entity.Users;
import com.bugrahan.noteapp.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final String BCRYPT_PREFIX = "$2a$";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Kayıt veya güncellemede şifre düz metin geliyorsa hash'lenir; zaten hash ise olduğu gibi bırakılır.
     */
    @Override
    public Users saveUser(Users user) {
        String rawPassword = user.getPassword();
        if (rawPassword != null && !rawPassword.isBlank() && !isAlreadyEncoded(rawPassword)) {
            user.setPassword(passwordEncoder.encode(rawPassword));
        }
        return userRepository.save(user);
    }

    private boolean isAlreadyEncoded(String password) {
        return password.startsWith(BCRYPT_PREFIX);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
}
