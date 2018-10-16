package com.connormcwood.studentrest.controller;

import com.connormcwood.studentrest.exception.ResourceNotFoundException;
import com.connormcwood.studentrest.model.Category;
import com.connormcwood.studentrest.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/all")
    public List<Category> getAllCategorys() {
        return categoryRepository.findAll();
    }

    @PostMapping
    public Category createCategory(@Valid @RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable(value = "id") Long categoryId) {
        return findCategoryByIdOrThrow(categoryId);
    }

    @PatchMapping("/{id}")
    public Category updateCategory(@PathVariable(value = "id") Long categoryId, @Valid @RequestBody Category categoryDetails) {
        Category category = findCategoryByIdOrThrow(categoryId);

        category.setTitle(categoryDetails.getTitle());
        category.setContent(categoryDetails.getContent());

        return categoryRepository.save(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long id) {
        Category category = findCategoryByIdOrThrow(id);
        categoryRepository.delete(category);
        return ResponseEntity.ok().build();
    }

    private Category findCategoryByIdOrThrow(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }
}
