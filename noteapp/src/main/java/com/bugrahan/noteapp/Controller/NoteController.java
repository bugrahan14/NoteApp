package com.bugrahan.noteapp.Controller;

import com.bugrahan.noteapp.Entity.Notes;
import com.bugrahan.noteapp.Service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@RequestMapping("/notes")
@Controller
public class NoteController {

    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Notes> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notes> getNoteById(@PathVariable Long id) {
        try {
            Notes note = noteService.getNoteById(id);
            return ResponseEntity.ok(note);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Notes> createNote(@RequestBody Notes note) {
        Notes saved = noteService.saveNote(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notes> updateNote(@PathVariable Long id, @RequestBody Notes note) {
        try {
            Notes existing = noteService.getNoteById(id);
            existing.setTitle(note.getTitle());
            existing.setContent(note.getContent());
            if (note.getUsers() != null) {
                existing.setUsers(note.getUsers());
            }
            Notes updated = noteService.updateNote(existing);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
