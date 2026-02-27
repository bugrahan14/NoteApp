package com.bugrahan.noteapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bugrahan.noteapp.Entity.Notes;
public interface NoteRepository extends JpaRepository<Notes , Long> {



}
