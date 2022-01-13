package com.totwgforum.gforum.controller;

import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.dto.user.UserDtoRes;
import com.totwgforum.gforum.dto.user.UserLoginFormReq;
import com.totwgforum.gforum.dto.user.UserSaveFormReq;
import com.totwgforum.gforum.repository.UserRepository;
import com.totwgforum.gforum.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rt;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void before() {
        UserSaveFormReq dto = new UserSaveFormReq();
        dto.setEmail("test#$#@dsd2.com");
        dto.setPassword("qwe123!@#");
        dto.setPasswordConfirm("qwe123!@#");
        dto.setNickName("test43asd4#$#");
        Long userId = userService.join(dto);
    }


    //@Test
    void 회원가입() {

        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("email", "43asd4#$#@dsd2.com");
        map.add("nickName", "43asd4#$#");
        map.add("password", "qwe123!@#");
        map.add("passwordConfirm", "qwe123!@#");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        String url = "http://localhost:" + port + "/user/create";
        // When
        ResponseEntity<String> responseEntity = rt.postForEntity( url, request , String.class );

        // Then
        UserDtoRes find = userService.findByEmail("43asd4#$#@dsd2.com");

        System.out.println("responseEntity = " + responseEntity.getBody());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(find.getEmail()).isEqualTo("43asd4#$#@dsd2.com");

    }

    //@Test
    void 로그인() {

        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("email", "test#$#@dsd2.com");
        map.add("password", "qwe123!@#");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        String url = "http://localhost:" + port + "/user/login";

        // When
        ResponseEntity<String> responseEntity = rt.postForEntity( url, request , String.class );
        System.out.println("responseEntity = " + responseEntity);

        String cookie = responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        System.out.println("Cookie = " + cookie);


        // Then

        String loginedOnlyUrl = "http://localhost:" + port + "/posts/create";

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        header.set(HttpHeaders.COOKIE, cookie);

        RequestEntity<Void> build = RequestEntity.get(URI.create(loginedOnlyUrl))
                .headers(header).build();

        ResponseEntity<String> res = rt.exchange(build, String.class);
        System.out.println("res = " + res);


        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody()).contains("test43asd4#$#");

    }

    //@Test
    void 로그아웃() {
        // Given

        // When

        // Then
    }

}