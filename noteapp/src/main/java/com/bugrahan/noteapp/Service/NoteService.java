package com.bugrahan.noteapp.Service;

import com.bugrahan.noteapp.Entity.Notes;
import java.util.Optional;

import java.util.List;
public interface NoteService {

    Notes getNoteById(Long id);

    Notes saveNote(Notes note);

    void deleteNote(Long id);

    List<Notes> getAllNotes();

    Notes updateNote(Notes note);
}
