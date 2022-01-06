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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired private UserService userService;

    @Test
    public void 회원가입(){
        // given
        String email = UUID.randomUUID().toString();
        String nick = UUID.randomUUID().toString();

        UserSaveFormReq dto = new UserSaveFormReq();
        dto.setEmail(email);
        dto.setPassword("1111");
        dto.setPasswordConfirm("1111");
        dto.setNickName(nick);

        // when
        Long joinedUserId = userService.join(dto);

        // then
        UserDtoRes byId = userService.findById(joinedUserId);
        Assertions.assertThat(email).isEqualTo(byId.getEmail());
    }

}