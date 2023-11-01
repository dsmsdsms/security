package org.example.service;

import org.example.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    User createUser(String username, String password);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    boolean authenticate(String username, String password);
    boolean deleteUser(String username);
    boolean updateRole(String username, String roleName);
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
