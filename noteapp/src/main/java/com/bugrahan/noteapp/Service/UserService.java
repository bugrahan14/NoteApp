package com.bugrahan.noteapp.Service;
import com.bugrahan.noteapp.Entity.Users;
import java.util.List;
import java.util.Optional;
public interface UserService {

    Optional<Users> getUserById(Long id);

    Users saveUser(Users user);

    void deleteUser(Long id);

    List<Users> getAllUsers();

    

    


}
