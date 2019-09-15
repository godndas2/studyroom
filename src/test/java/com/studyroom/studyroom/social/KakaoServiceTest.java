package com.studyroom.studyroom.social;

import com.studyroom.studyroom.model.social.KakaoProfile;
import com.studyroom.studyroom.service.user.KakaoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KakaoServiceTest {

    @Autowired
    private KakaoService kakaoService;

    @Test
    public void kakaoProfile_returnProfile() {
        String access_token = "UL7tLCtj6OU8NW_vOtMn3HwFCxVhdhC1I16GjAo9dNoAAAFtMFbLsQ";
        KakaoProfile profile = kakaoService.getKakaoProfile(access_token);
        // then
        assertNotNull(profile);
        assertEquals(profile.getId(), Long.valueOf(1066788171));
    }
}
