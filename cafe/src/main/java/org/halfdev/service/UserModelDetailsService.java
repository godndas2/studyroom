package org.halfdev.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.halfdev.exception.UserNotActivatedException;
import org.halfdev.repository.UserRepository;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
* @author halfdev
* @since 2019-11-26
* Authentication a user from the DB
*/
@Component("userDetailsService")
@Slf4j
@RequiredArgsConstructor
public class UserModelDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authentication user '{}'", login);

        if (new EmailValidator().isValid(login, null)) {
            return userRepository.findOneWithAuthoritiesByEmailIgnoreCase(login)
                    .map(user -> createSpringSecurityUser(login, user))
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + "was not found"));
        }

        String lowercaselogin = login.toLowerCase(Locale.ENGLISH);
        return userRepository.findOneWithAuthoritiesByUsername(lowercaselogin)
        .map(user -> createSpringSecurityUser(lowercaselogin, user))
                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaselogin + "was not found"));
    }

    private User createSpringSecurityUser(String lowercaselogin, org.halfdev.model.User user) {

        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + lowercaselogin + "was not found");
        }

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

        return new User(user.getUsername()
        , user.getPassword()
        , grantedAuthorities);


    }
}
