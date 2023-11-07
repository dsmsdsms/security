package org.example.service;

import org.example.model.Category;
import org.example.model.Subscription;
import org.example.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubscriptionService subscriptionService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, SubscriptionService subscriptionService) {
        this.categoryRepository = categoryRepository;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public List<Category> getAllCategories() {      // Get all available categories
        return categoryRepository.findAll();
    }

    @Override
    public Map<String, Object> getCategoriesData(String username) {
        List<Category> allCategories = getAllCategories();
        List<Subscription> userSubscriptions = subscriptionService.getSubscriptionsByUsername(username);  // Get all subscriptions for a given user

        List<Map<String, Object>> subscribedCategoriesList = new ArrayList<>();
        for (Subscription subscription : userSubscriptions) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", subscription.getId());
            map.put("name", subscription.getCategory().getName());
            map.put("remainingContent", subscription.getRemainingContent());
            map.put("price", subscription.getPrice());
            map.put("startDate", subscription.getStartDate().toString());
            map.put("startPaymentDate", subscription.getStartPaymentDate().toString());

            if (subscription.getSharedWith() != null) {
                map.put("sharedWith", subscription.getSharedWith().getUsername());
            } else {
                map.put("sharedWith", "");
            }
            subscribedCategoriesList.add(map);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("availableCategories", allCategories);
        response.put("subscribedCategories", subscribedCategoriesList);

        return response;
    }
}
