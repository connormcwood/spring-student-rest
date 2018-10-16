package com.connormcwood.studentrest.repository;

import com.connormcwood.studentrest.model.Category;
import com.connormcwood.studentrest.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
