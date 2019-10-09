package com.oauth.module.oauthmodule.repository;

import com.oauth.module.oauthmodule.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepo extends JpaRepository<User, Long> {

}
