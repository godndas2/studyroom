package org.halfdev.security;

import lombok.extern.slf4j.Slf4j;
import org.halfdev.utils.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class SecurityUtilsTest {

    @Test
    public void getCurrentUsername() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);

        Optional<String> username = SecurityUtils.getCurrentUsername();

        assertThat(username).contains("admin");
    }

    @Test
    public void getCurrentUsernameForNoAuthenticationInContext() {

        Optional<String> username = SecurityUtils.getCurrentUsername();

        assertThat(username).isEmpty();
        log.debug("" + username);
    }
}
