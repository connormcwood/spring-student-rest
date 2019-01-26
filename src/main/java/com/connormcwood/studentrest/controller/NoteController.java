package com.connormcwood.studentrest.controller;

import com.connormcwood.studentrest.exception.ChildResourceNotFoundException;
import com.connormcwood.studentrest.exception.ResourceNotFoundException;
import com.connormcwood.studentrest.model.Note;
import com.connormcwood.studentrest.repository.NoteRepository;
import com.connormcwood.studentrest.repository.PriorityRepository;
import com.connormcwood.studentrest.repository.UserRepository;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PriorityRepository priorityRepository;

    @GetMapping("/all")
    public List<Note> getAllNotes() {
        return noteRepository.findAllByOrderByOrderAsc();
    }

    @PostMapping
    public Note createNote(@Valid @RequestBody Note note) {
        System.out.println(note.getPriority().getId());

        note.setPriority(priorityRepository.findById(note.getPriority().getId()).orElseThrow(() -> new ChildResourceNotFoundException("Note", "id", note.getPriority().getId(), "Priority")));

        if(note.getUser() != null) {
            note.setUser(userRepository.findById(note.getUser().getId()).orElseThrow(() -> new ChildResourceNotFoundException("Note", "id", note.getUser().getId(), "User")));
        }

        note.setTitle(note.getTitle());
        note.setContent(note.getContent());

        return noteRepository.save(note);
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable(value = "id") Long noteId) {
        return findNoteByIdOrThrow(noteId);
    }

    @PatchMapping("/{id}")
    public Note updateNote(@PathVariable(value = "id") Long noteId, @Valid @RequestBody Note noteDetails) {
        Note note = findNoteByIdOrThrow(noteId);
        note.setPriority(priorityRepository.findById(note.getPriority().getId()).orElseThrow(() -> new ChildResourceNotFoundException("Note", "id", note.getPriority().getId(), "Priority")));
        if(note.getUser() != null) {
            note.setUser(userRepository.findById(note.getUser().getId()).orElseThrow(() -> new ChildResourceNotFoundException("Note", "id", note.getUser().getId(), "User")));
        }
        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());

        return noteRepository.save(note);
    }

    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteNotes(@PathVariable List<Integer> ids) {
        for(Integer id : ids) {
            Note note = findNoteByIdOrThrow(Long.valueOf(id));
            noteRepository.delete(note);
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/reorder", method = RequestMethod.PATCH)
    public ResponseEntity<?> reorderNotes(@RequestBody String body) {
        try {
            JSONArray jsonArray = new JSONArray(body);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                System.out.println(jsonObject.toString());
                Long noteId = new Long((Integer)jsonObject.get("id"));
                Long orderId = new Long((Integer)jsonObject.get("order"));

                Note note = findNoteByIdOrThrow(noteId);
                note.setOrder(orderId);
                noteRepository.save(note);
            }
            System.out.println("Heres");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(body);

        return null;
    }

    private Note findNoteByIdOrThrow(Long noteId) {
        return noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }
}
