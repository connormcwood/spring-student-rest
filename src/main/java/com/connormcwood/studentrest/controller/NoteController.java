package com.connormcwood.studentrest.controller;

import com.connormcwood.studentrest.exception.ResourceNotFoundException;
import com.connormcwood.studentrest.model.Note;
import com.connormcwood.studentrest.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    NoteRepository noteRepository;

    @GetMapping("/all")
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @PostMapping
    public Note createNode(@Valid @RequestBody Note note) {
        return noteRepository.save(note);
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable(value = "id") Long noteId) {
        return findNoteByIdOrThrow(noteId);
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable(value = "id") Long noteId, @Valid @RequestBody Note noteDetails) {
        Note note = findNoteByIdOrThrow(noteId);

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());

        return noteRepository.save(note);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
        Note note = findNoteByIdOrThrow(noteId);
        noteRepository.delete(note);
        return ResponseEntity.ok().build();
    }

    private Note findNoteByIdOrThrow(Long noteId) {
        return noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }
}
