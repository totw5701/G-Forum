package com.totwgforum.gforum.repository;

import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.domain.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    
    @Test
    void 이메일로_찾기() {
        // Given
        User entity = new User();
        entity.setEmail("test1234@test.com");
        entity.setPassword("1111");
        entity.setNickName("testNick");
        entity.setRegisterDate(LocalDateTime.now());
        entity.setRole(UserRole.ROLE_USER);
        
        userRepository.save(entity);
        
        // When
        User user = userRepository.findByEmail("test1234@test.com");

        // Then
        System.out.println("user.getEmail() = " + user.getEmail());
        System.out.println("user.getRole() = " + user.getRole());
        System.out.println("user.getNickName() = " + user.getNickName());
        Assertions.assertThat(user.getEmail()).isEqualTo("test1234@test.com");
    }
    
    

}