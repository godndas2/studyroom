package com.studyroom.studyroom.repository;

import com.studyroom.studyroom.model.User;
import com.studyroom.studyroom.model.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUid(String email);
    // uid 및 provider 정보로 social 회원정보 조회
    Optional<User> findByUidAndProvider(String uid, String provider);

    User findBySocial(UserConnection userConnection);
}
