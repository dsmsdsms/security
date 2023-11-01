package org.example.repository;

import org.example.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserUsername(String username);
    List<Subscription> findByUserOrSharedWith(User user, User sharedWith);
}
