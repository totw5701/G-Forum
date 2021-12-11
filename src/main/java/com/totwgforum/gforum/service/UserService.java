package com.totwgforum.gforum.service;

import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.domain.UserRole;
import com.totwgforum.gforum.dto.user.UserDtoRes;
import com.totwgforum.gforum.dto.user.UserSaveFormReq;
import com.totwgforum.gforum.repository.UserRepository;
import com.totwgforum.gforum.util.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long join(UserSaveFormReq form){

        User user = new User();
        user.setEmail(form.getEmail());
        user.setNickName(form.getNickName());
        user.setRegisterDate(LocalDateTime.now());

        String secPwd = bCryptPasswordEncoder.encode(form.getPassword());
        user.setPassword(secPwd);

        user.setRole(UserRole.ROLE_USER);

        userRepository.save(user);
        return user.getId();
    }

    public UserDtoRes findById(Long userId){
        User entity = userRepository.findOne(userId);

        UserDtoRes user = new UserDtoRes();
        user.setId(entity.getId());
        user.setEmail(entity.getEmail());
        user.setNickName(entity.getNickName());
        user.setPassword(entity.getPassword());
        user.setRegisterDate(entity.getRegisterDate());

        return user;
    }

    public UserDtoRes findByEmail(String email){

        User entity = userRepository.findByEmail(email);

        if (entity == null) {
            return null;
        } else {
            UserDtoRes user = new UserDtoRes();
            user.setId(entity.getId());
            user.setEmail(entity.getEmail());
            user.setNickName(entity.getNickName());
            user.setPassword(entity.getPassword());
            user.setRegisterDate(entity.getRegisterDate());

            return user;
        }
    }

    public UserDtoRes findByNickName(String nickName){
        User entity = userRepository.findByNickName(nickName);

        if (entity == null) {
            return null;
        } else {
            UserDtoRes user = new UserDtoRes();
            user.setId(entity.getId());
            user.setEmail(entity.getEmail());
            user.setNickName(entity.getNickName());
            user.setPassword(entity.getPassword());
            user.setRegisterDate(entity.getRegisterDate());

            return user;
        }
    }

    // 테스트용
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
