package org.example.service;

import org.example.model.Role;
import org.example.model.Subscription;
import org.example.model.User;
import org.example.repository.SubscriptionRepository;
import org.example.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SubscriptionRepository subscriptionRepository;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singleton(Role.USER));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            List<Subscription> subscriptions = subscriptionRepository.findByUserUsername(username);
            if (subscriptions.isEmpty()) {
                userRepository.delete(user);
                return true;
            } else {
                return false;  // if user has subscription
            }
        }
        return false;
    }

    @Override
    public boolean updateRole(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            try {
                Role role = Role.valueOf(roleName);
                Set<Role> roles = new HashSet<>();
                roles.add(role);
                user.setRoles(roles);
                userRepository.save(user);
                return true;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid role name: " + roleName);
            }
        }
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
