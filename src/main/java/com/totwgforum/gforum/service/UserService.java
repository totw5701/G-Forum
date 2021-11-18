package com.totwgforum.gforum.service;

import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public Long join(User user){
        userRepository.save(user);
        return user.getId();
    }

    public User findById(Long userId){
        return userRepository.findOne(userId);
    }

    public User findByEmail(String email){
        Optional<User> findUser = userRepository.findAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
        if (findUser.isEmpty()) {
            return null;
        } else {
            return findUser.get();
        }
    }

    public User findByNickName(String nickName){
        Optional<User> findUser = userRepository.findAll().stream()
                .filter(user -> user.getNickName().equals(nickName))
                .findFirst();
        if (findUser.isEmpty()) {
            return null;
        } else {
            return findUser.get();
        }
    }


}
