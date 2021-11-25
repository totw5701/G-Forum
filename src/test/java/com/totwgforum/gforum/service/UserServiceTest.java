package com.totwgforum.gforum.service;

import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.dto.user.UserDtoRes;
import com.totwgforum.gforum.dto.user.UserSaveFormReq;
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
class UserServiceTest {

    @Autowired private UserService userService;

    @Test
    public void 회원가입(){
        // given

        UserSaveFormReq dto = new UserSaveFormReq();
        dto.setEmail("email@email.com");
        dto.setPassword("1111");
        dto.setPasswordConfirm("1111");
        dto.setNickName("nickname");

        // when
        Long joinedUserId = userService.join(dto);

        // then
        UserDtoRes byId = userService.findById(joinedUserId);
        Assertions.assertThat(dto.getEmail()).isEqualTo(byId.getEmail());
    }

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