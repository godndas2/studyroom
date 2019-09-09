package com.studyroom.studyroom.repo;

import com.studyroom.studyroom.model.User;
import com.studyroom.studyroom.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
/*
* @DataJpaTest : @Entitiy를 읽어 Repository 내용을 Test 할 수 있도록 환경을 만들어준다.
* */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserJpaRepoTest {

    /*
    *   < assert method >
        assertNotNull(obj), assertNotNull(obj)
        객체(obj)의 Null여부를 테스트
        assertTrue(condition), assertFalse(condition)
        조건(condition)의 참/거짓 테스트
        assertEquals(obj1, obj2), assertNotEquals(obj1, obj2)
        obj1와 obj2의 값이 같은지 테스트
        assertSame(obj1, obj2)
        obj1과 obj2의 객체가 같은지 테스트
        assertArrayEquals(arr1,arr2)
        배열 arr1과 arr2가 같은지 확인한다.
        assertThat(T actual, Matcher matcher)
        첫번째 인자에 비교대상 값을 넣고, 두번째 로직에는 비교로직을 넣어 테스트.
        ex) assertThat(a, is(100)) : a의 값이 100인가?
        ex) assertThat(obj, is(nullValue())); 객체가 null인가?
    *
    * */
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void whenFindByUid_thenReturnUser() {
        String uid = "halfdev@gmail.com";
        String name = "halfdev";
        // given
        userRepository.save(User.builder()
        .uid(uid)
        .name(name)
        .password("1234")
        .roles(Collections.singletonList("ROLE_USER"))
        .build());
        // when
        Optional<User> user = userRepository.findByUid(uid);
        // then
        assertNotNull(user); // user 객체가 null인지 체크
        assertTrue(user.isPresent()); // user 객체의 존재 여부 true / false
        assertEquals(user.get().getName(), name); // user 객체의 name과 name변수 값이 같은지 체크
        assertThat(user.get().getName(), is(name)); // user 객체의 name과 name변수 값이 같은지 체크
    }
}
