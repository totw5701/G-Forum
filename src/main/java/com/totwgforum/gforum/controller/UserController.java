package com.totwgforum.gforum.controller;

import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.dto.post.PostSaveFormReq;
import com.totwgforum.gforum.dto.user.UserSaveFormReq;
import com.totwgforum.gforum.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/create")
    public String createUser(Model model){
        model.addAttribute("user" ,new UserSaveFormReq());
        return "/user/create";
    }

    @PostMapping("/user/create")
    public String createUserProcess(@Validated @ModelAttribute("user")UserSaveFormReq form, BindingResult bindingResult){

        // 비밀번호 보안 어떻게 해야하는지 찾아볼 것.

        if(form.getPasswordConfirm() != null && form.getPassword() != null) {
            if (!form.getPassword().equals(form.getPasswordConfirm())) {
                bindingResult.reject("passwordConfirm");
            }
        }

        if(form.getEmail() != null){
            User findUser = userService.findByEmail(form.getEmail());
            System.out.println("findUser = " + findUser);
            if(findUser != null){
                bindingResult.reject("emailDuplicate");
            }
        }

        if(form.getNickName() != null){
            User findUser = userService.findByNickName(form.getNickName());
            if(findUser != null){
                bindingResult.reject("nickNameDuplicate");
            }
        }

        if(bindingResult.hasErrors()){
            log.info("error={}", bindingResult);
            return "user/create";
        }

        User user = new User();
        user.setEmail(form.getEmail());
        user.setNickName(form.getNickName());
        user.setRegisterDate(LocalDateTime.now());
        user.setPassword(form.getPassword());

        userService.join(user);
        return "redirect:/";
    }


}
