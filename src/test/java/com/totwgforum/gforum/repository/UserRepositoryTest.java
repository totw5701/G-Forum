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
    void 저장_및_이메일로_찾기() {
        // Given
        User entity = new User();
        entity.setEmail("dhfgiy3uf3!@#@test.com");
        entity.setPassword("1111");
        entity.setNickName("testNick");
        entity.setRegisterDate(LocalDateTime.now());
        entity.setRole(UserRole.ROLE_USER);
        
        // When
        userRepository.save(entity);
        User user = userRepository.findByEmail("dhfgiy3uf3!@#@test.com");

        // Then
        Assertions.assertThat(user.getEmail()).isEqualTo("dhfgiy3uf3!@#@test.com");
    }
}