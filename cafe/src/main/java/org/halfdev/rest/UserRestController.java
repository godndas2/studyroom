package org.halfdev.rest;

import lombok.RequiredArgsConstructor;
import org.halfdev.model.User;
import org.halfdev.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<User> getActiveUser() {
        return ResponseEntity.ok(userService.getUserWithAuthorities().get());
    }
}
