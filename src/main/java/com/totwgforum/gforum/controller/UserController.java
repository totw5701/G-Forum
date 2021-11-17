package com.totwgforum.gforum.controller;

import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.dto.post.PostSaveFormReq;
import com.totwgforum.gforum.dto.user.UserLoginFormReq;
import com.totwgforum.gforum.dto.user.UserSaveFormReq;
import com.totwgforum.gforum.service.UserService;
import com.totwgforum.gforum.util.SHA256;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String createUserProcess(@Validated @ModelAttribute("user")UserSaveFormReq form,
                                    BindingResult bindingResult){

        // 비밀번호 보안 어떻게 해야하는지 찾아볼 것.

        if(bindingResult.hasErrors()){
            log.info("error={}", bindingResult);
            return "user/create";
        }

        if (!form.getPassword().equals(form.getPasswordConfirm())) {
            bindingResult.reject("passwordConfirm");
        }

        User findUser = userService.findByEmail(form.getEmail());
        System.out.println("findUser = " + findUser);
        if(findUser != null){
            bindingResult.reject("emailDuplicate");
        }

        User findByNickName = userService.findByNickName(form.getNickName());
        if(findByNickName != null){
            bindingResult.reject("nickNameDuplicate");
        }

        if(bindingResult.hasErrors()){
            log.info("error={}", bindingResult);
            return "user/create";
        }


        User user = new User();
        user.setEmail(form.getEmail());
        user.setNickName(form.getNickName());
        user.setRegisterDate(LocalDateTime.now());

        String secuPW = SHA256.convert(form.getPassword());
        user.setPassword(secuPW);

        userService.join(user);
        return "redirect:/";
    }

    @GetMapping("/user/login")
    public String login(Model model) {
        model.addAttribute("user", new UserLoginFormReq());
        return "user/login";
    }

    @PostMapping("/user/login")
    public String loginProcess(@Validated @ModelAttribute("user") UserLoginFormReq form,
                               BindingResult bindingResult,
                               HttpServletRequest request){

        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return "user/login";
        }

        User findByEmailUser = userService.findByEmail(form.getEmail());
        if (findByEmailUser == null) {
            bindingResult.reject("nonUserEmail");
        } else if (!SHA256.convert(form.getPassword()).equals(findByEmailUser.getPassword())) {
            bindingResult.reject("passwordNotMatch");
        }

        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return "user/login";
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginUser", findByEmailUser);
        return "redirect:/";
    }

    @GetMapping("/user/logout")
    public String logout(HttpServletRequest req){

        log.info("logout!");
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
