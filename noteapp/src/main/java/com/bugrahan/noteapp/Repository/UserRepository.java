package com.bugrahan.noteapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bugrahan.noteapp.Entity.Users;

public interface UserRepository extends JpaRepository<Users , Long> {

}
