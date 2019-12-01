package org.halfdev.rest;

import lombok.RequiredArgsConstructor;
import org.halfdev.model.User;
import org.halfdev.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;

    @GetMapping("/signuppage")
    @ResponseBody
    public ModelAndView signUppage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @PostMapping("/signup")
    public String signUp(User user) {
        userService.signUp(user);
        return "Signup Success..!";
    }


    @GetMapping("/user")
    public ResponseEntity<User> getActiveUser() {
        return ResponseEntity.ok(userService.getUserWithAuthorities().get());
    }
}
