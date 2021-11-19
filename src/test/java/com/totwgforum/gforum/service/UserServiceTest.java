package com.totwgforum.gforum.service;

import com.totwgforum.gforum.domain.User;
import org.apache.tomcat.jni.Local;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class UserServiceTest {

    @Autowired private UserService userService;

//    @Test
//    public void 회원가입(){
//        // given
//        User user1 = new User();
//        user1.setEmail("email@naver.com");
//        user1.setNickName("pinkHero");
//        user1.setRegisterDate(LocalDateTime.now());
//
//        // when
//        Long joinedUserId = userService.join(user1);
//
//        // then
//        User joinedUser = userService.findById(joinedUserId);
//        Assertions.assertThat(joinedUser.getId()).isEqualTo(joinedUserId);
//    }

//    @Test
//    public void 회원조회(){
//        // given
//        User user1 = new User();
//        user1.setEmail("email@naver.com");
//        user1.setNickName("pinkHero");
//        user1.setRegisterDate(LocalDateTime.now());
//        Long joinedUserId = userService.join(user1);
//
//        // when
//        User findedUser = userService.findById(joinedUserId);
//
//        // then
//        Assertions.assertThat(findedUser.getId()).isEqualTo(joinedUserId);
//    }
}