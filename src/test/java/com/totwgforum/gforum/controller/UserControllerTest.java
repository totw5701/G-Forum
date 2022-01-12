package com.totwgforum.gforum.controller;

import com.totwgforum.gforum.dto.user.UserDtoRes;
import com.totwgforum.gforum.dto.user.UserSaveFormReq;
import com.totwgforum.gforum.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@WebMvcTest(controllers = UserController.class)
@SpringBootTest
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserService userService;


    @Test
    void 회원가입_페이지() {




        // Given

        // When

        // Then

    }

    @Test
    void 로그인() {
        // Given

        // When

        // Then


    }

    @Test
    void 로그아웃() {
        // Given

        // When

        // Then
    }

}