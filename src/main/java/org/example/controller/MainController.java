package org.example.controller;


import org.example.model.Category;
import org.example.model.User;
import org.example.repository.CategoryRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Model model) {
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @RequestParam String name,
            @RequestParam Integer availableContent,
            @RequestParam Double price,
            Model model) {

        Category category = new Category(name, availableContent, price);
        categoryRepository.save(category);
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "main";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());          // getting all users
        model.addAttribute("users", users); // adding a list of users to the model
        return "admin";                                 // returns "admin.mustache"
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "categories";
    }
}
