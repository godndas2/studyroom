package com.studyroom.studyroom.repository;

import com.studyroom.studyroom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
