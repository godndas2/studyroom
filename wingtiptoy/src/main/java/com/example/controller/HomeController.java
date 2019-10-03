package com.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Controller
//@RequiredArgsConstructor
public class HomeController {

//    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @PreAuthorize("hasRole('Users')")
    @GetMapping("/")
    @ResponseBody
    public String user() {
        return "Hello Users";
    }

//    @PreAuthorize("hasRole('Users2')")
//    @GetMapping("Users2")
//    @ResponseBody
//    public String user2() {
//        return "Hello Users2";
//    }

//    @GetMapping("/")
//    public String index(Model model, OAuth2AuthenticationToken authentication) {
//        final OAuth2AuthorizedClient authorizedClient =
//                this.oAuth2AuthorizedClientService.loadAuthorizedClient(
//                        authentication.getAuthorizedClientRegistrationId(),
//                        authentication.getName());
//        model.addAttribute("userName", authentication.getName());
//        model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
//        return "test";
//    }
}
