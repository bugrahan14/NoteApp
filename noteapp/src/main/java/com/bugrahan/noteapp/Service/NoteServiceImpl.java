package com.bugrahan.noteapp.Service;

import com.bugrahan.noteapp.Entity.Notes;
import com.bugrahan.noteapp.Repository.NoteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public void deleteNote(Long id) {
        
        noteRepository.deleteById(id);
        
    }

    @Override
    public List<Notes> getAllNotes() {
        
        return noteRepository.findAll();
    }

    @Override
    public Notes getNoteById(Long id) {

        Optional<Notes> note = noteRepository.findById(id);

        Notes notes = null ;

        if (note.isPresent()) {
            notes =  note.get();
       
    }

    else {

        throw new RuntimeException("Note not found");
    }

    return notes;
    }

    @Override
    public Notes saveNote(Notes note) {
        return noteRepository.save(note);
    }

    @Override
    public Notes updateNote(Notes note) {
        return noteRepository.save(note);
    }

}
