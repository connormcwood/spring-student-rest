package com.connormcwood.studentrest.controller;

import com.connormcwood.studentrest.exception.ResourceNotFoundException;
import com.connormcwood.studentrest.model.Priority;
import com.connormcwood.studentrest.model.Priority;
import com.connormcwood.studentrest.repository.CategoryRepository;
import com.connormcwood.studentrest.repository.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/priority")
public class PriorityController {

    @Autowired
    PriorityRepository priorityRepository;

    @GetMapping("/all")
    public List<Priority> getAllCategorys() {
        return priorityRepository.findAll();
    }

    @PostMapping
    public Priority createCategory(@Valid @RequestBody Priority category) {
        return priorityRepository.save(category);
    }

    @GetMapping("/{id}")
    public Priority getCategoryById(@PathVariable(value = "id") Long categoryId) {
        return findCategoryByIdOrThrow(categoryId);
    }

    @PatchMapping("/{id}")
    public Priority updateCategory(@PathVariable(value = "id") Long categoryId, @Valid @RequestBody Priority categoryDetails) {
        Priority category = findCategoryByIdOrThrow(categoryId);

        category.setTitle(categoryDetails.getTitle());
        category.setContent(categoryDetails.getContent());

        return priorityRepository.save(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long id) {
        Priority category = findCategoryByIdOrThrow(id);
        priorityRepository.delete(category);
        return ResponseEntity.ok().build();
    }

    private Priority findCategoryByIdOrThrow(Long id) {
        return priorityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Priority", "id", id));
    }
}
