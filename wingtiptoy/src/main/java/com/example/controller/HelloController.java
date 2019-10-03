//package com.example.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//@RequiredArgsConstructor
//public class HelloController {
//
//    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
//
//    @PreAuthorize("hasRole('모든 사용자')")
//    @GetMapping("/user")
//    @ResponseBody
//    public String helloWorld() {
//        return "It's User-s!";
//    }
//
//    @GetMapping("/")
//    public String index(Model model, OAuth2AuthenticationToken authentication) {
//        final OAuth2AuthorizedClient authorizedClient =
//                this.oAuth2AuthorizedClientService.loadAuthorizedClient(
//                        authentication.getAuthorizedClientRegistrationId(),
//                        authentication.getName());
//        model.addAttribute("userName", authentication.getName());
//        model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
//        return "index";
//    }
//}