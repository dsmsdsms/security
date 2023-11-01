package org.example.controller;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserRegistrationDto;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    //----- the method takes username&password as parameter -----

//    @PostMapping("/registration")
//    public ResponseEntity<?> signUp(@RequestParam String username, @RequestParam String password) {
//        User user = userService.createUser(username, password);
//        return ResponseEntity.ok(user);
//    }

    //----- method takes username&password as JSON through data transfer object -----

    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody UserRegistrationDto registrationDto) {
        Map<String, String> response = new HashMap<>();

        if (userService.existsByUsername(registrationDto.getUsername())) {
            response.put("errorMessage", "User exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        User user = userService.createUser(registrationDto.getUsername(), registrationDto.getPassword());
        response.put("successMessage", "User added successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/delete/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        boolean isDeleted = userService.deleteUser(username);
        if (isDeleted) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/admin"));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User has subscriptions");
        }
    }


    @PostMapping("/updateRole/{username}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateRole(@PathVariable String username, @RequestParam String role) {
        boolean isUpdated = userService.updateRole(username, role);
        if (isUpdated) {
            return ResponseEntity.ok("Role updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }


}
