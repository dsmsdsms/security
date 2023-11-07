//package org.example.service;
//
//import org.example.model.Category;
//import org.example.model.Subscription;
//import org.example.repository.CategoryRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//public class CategoryService {
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//    @Autowired
//    private SubscriptionService subscriptionService;
//    public List<Category> getAllCategories() {
//        return categoryRepository.findAll();
//    }
//    public Map<String, Object> getCategoriesData(String username) {
//
//        List<Category> allCategories = getAllCategories();      // Get all available categories
//        List<Subscription> userSubscriptions = subscriptionService.getSubscriptionsByUsername(username); // Get all subscriptions for a given user
//
//        // Convert the user's subscriptions to a list of categories
//        List<Map<String, Object>> subscribedCategoriesList = new ArrayList<>();
//        for (Subscription subscription : userSubscriptions) {
//            Map<String, Object> map = new LinkedHashMap<>();
//            map.put("id", subscription.getId());
//            map.put("name", subscription.getCategory().getName());
//            map.put("remainingContent", subscription.getRemainingContent());
//            map.put("price", subscription.getPrice());
//            map.put("startDate", subscription.getStartDate().toString());
//            map.put("startPaymentDate", subscription.getStartPaymentDate().toString());
//
//        if (subscription.getSharedWith() != null) {
//            map.put("sharedWith", subscription.getSharedWith().getUsername());
//        } else {
//            map.put("sharedWith", ""); // if the value is NULL, then inserts an empty string
//        }
//            subscribedCategoriesList.add(map);
//        }
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("availableCategories", allCategories);
//        response.put("subscribedCategories", subscribedCategoriesList);
//
//        return response;
//    }
//}
