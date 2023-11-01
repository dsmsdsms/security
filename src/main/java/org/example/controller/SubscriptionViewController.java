package org.example.controller;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/subscriptionsView")
public class SubscriptionViewController {

    private final UserRepository userRepository;
    private final CategoryService categoryService;

    // inject into the constructor
    @Autowired
    public SubscriptionViewController(UserRepository userRepository, CategoryService categoryService) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getSubscriptionsView(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();

        Map<String, Object> categoriesData = categoryService.getCategoriesData(username);

        model.addAttribute("availableCategories", categoriesData.get("availableCategories"));
        model.addAttribute("subscribedCategories", categoriesData.get("subscribedCategories"));

        System.out.println(categoriesData.get("subscribedCategories"));

//        List<User> users = userRepository.findAll();
        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        model.addAttribute("users", users);

        return "subscriptionsView";
    }
}
