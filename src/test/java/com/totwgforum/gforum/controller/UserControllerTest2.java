package com.totwgforum.gforum.controller;

import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.dto.user.UserDtoRes;
import com.totwgforum.gforum.dto.user.UserLoginFormReq;
import com.totwgforum.gforum.dto.user.UserSaveFormReq;
import com.totwgforum.gforum.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class UserControllerTest2 {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rt;

    @Autowired
    UserService userService;


    //@Test
    void 회원가입() {


        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("email", "email@email.com");
        map.add("nickName", "nickname");
        map.add("password", "1111");
        map.add("passwordConfirm", "1111");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        String url = "http://localhost:" + port + "/user/create";
        // When
        ResponseEntity<String> responseEntity = rt.postForEntity( url, request , String.class );

        // Then
        UserDtoRes find = userService.findByEmail("email@email.com");

        System.out.println("responseEntity = " + responseEntity.getBody());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);

        assertThat(find.getEmail()).isEqualTo("email@email.com");
    }

    //@Test
    void 로그인() {
        // Given
        UserSaveFormReq dto = new UserSaveFormReq();
        dto.setEmail("email@email.com");
        dto.setPassword("1111");
        dto.setPasswordConfirm("1111");
        dto.setNickName("nickname");
        Long userId = userService.join(dto);

        UserDtoRes byId = userService.findByEmail("email@email.com");
        assertThat(dto.getEmail()).isEqualTo(byId.getEmail());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("email", "email@email.com");
        map.add("password", "1111");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        String url = "http://localhost:" + port + "/user/login";

        // When
        ResponseEntity<String> responseEntity = rt.postForEntity( url, request , String.class );
        System.out.println("responseEntity = " + responseEntity);

        String loginedOnlyUrl = "http://localhost:" + port + "/posts/create";

        ResponseEntity<String> loginOnlyPageRes = rt.getForEntity(loginedOnlyUrl, String.class);

        System.out.println("loginOnlyPageRes = " + loginOnlyPageRes);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginOnlyPageRes.getBody()).contains("nickname");

    }

    //@Test
    void 로그아웃() {
        // Given

        // When

        // Then
    }

}