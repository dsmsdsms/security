package org.example.controller;

import org.example.model.Category;
import org.example.repository.CategoryRepository;
import org.example.repository.SubscriptionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public CategoryController(
            CategoryRepository categoryRepository,
            SubscriptionRepository subscriptionRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    protected static final Logger logger = LogManager.getLogger(CategoryController.class);

    // Endpoint
    // http://localhost:8080/category/user
    // GET
    @GetMapping("/currentUsername")
    public ResponseEntity<String> getCurrentUsername(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userDetails.getUsername());
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory.isPresent()) {
            String message = "A category with the same name already exists: " + category.getName();
            System.out.println(message);
            return ResponseEntity.badRequest().body(message);
        }
        categoryRepository.save(category);
        String message = "Category successfully created";
        System.out.println(message);
        return ResponseEntity.ok(message);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id, @RequestHeader(name = "Accept") String acceptHeader) {
        if (!categoryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryRepository.deleteById(id);

        if ("text/plain".equals(acceptHeader)) {
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Category successfully deleted");
        } else {
            return ResponseEntity.ok(Map.of("message", "Category successfully deleted"));
        }
    }
}
