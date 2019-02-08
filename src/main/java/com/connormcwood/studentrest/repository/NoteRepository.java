package com.connormcwood.studentrest.repository;

import com.connormcwood.studentrest.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<Note> findById(Long id);
    List<Note> findAllByOrderByOrderAsc();

    @Query(value = "SELECT max(order_id) FROM notes", nativeQuery = true)
    int findHighestOrderId();
}
