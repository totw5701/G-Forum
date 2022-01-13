package com.totwgforum.gforum.controller;

import com.totwgforum.gforum.dto.user.UserDtoRes;
import com.totwgforum.gforum.dto.user.UserDtoSession;
import com.totwgforum.gforum.dto.user.UserLoginFormReq;
import com.totwgforum.gforum.dto.user.UserSaveFormReq;
import com.totwgforum.gforum.service.UserService;
import com.totwgforum.gforum.util.SHA256;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/user/create")
    public String createUser(Model model){
        model.addAttribute("user" ,new UserSaveFormReq());

        System.out.println("UserController.createUser");
        return "user/create";
    }

    @PostMapping("/user/create")
    public String createUserProcess(@Validated @ModelAttribute("user")UserSaveFormReq form,
                                    BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("error={}", bindingResult);
            return "user/create";
        }

        if (!form.getPassword().equals(form.getPasswordConfirm())) {
            bindingResult.reject("passwordConfirm");
        }

        UserDtoRes findUser = userService.findByEmail(form.getEmail());
        if(findUser != null){
            bindingResult.reject("emailDuplicate");
        }

        UserDtoRes findByNickName = userService.findByNickName(form.getNickName());
        if(findByNickName != null){
            bindingResult.reject("nickNameDuplicate");
        }

        if(bindingResult.hasErrors()){
            log.info("error={}", bindingResult);
            return "user/create";
        }

        userService.join(form);
        return "redirect:/";
    }

    @GetMapping("/user/login")
    public String login(Model model) {
        model.addAttribute("user", new UserLoginFormReq());
        return "user/login";
    }

    @PostMapping("/login-error")
    public String loginError(HttpServletRequest request, Model model) {

        model.addAttribute("user", new UserLoginFormReq());

        return "user/login";
    }

    // Spring Security로 변경 @PostMapping("/user/login")
    public String loginProcess(@Validated @ModelAttribute("user") UserLoginFormReq form,
                               BindingResult bindingResult,
                               HttpServletRequest request){

        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return "user/login";
        }

        UserDtoRes findByEmailUser = userService.findByEmail(form.getEmail());
        if (findByEmailUser == null) {
            bindingResult.reject("nonUserEmail");
        } else if (!bCryptPasswordEncoder.encode(form.getPassword()).equals(findByEmailUser.getPassword())) {
            bindingResult.reject("passwordNotMatch");
        }

        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return "user/login";
        }

        HttpSession session = request.getSession();
        UserDtoSession userSession = new UserDtoSession();
        userSession.setId(findByEmailUser.getId());
        userSession.setNickName(findByEmailUser.getNickName());
        session.setAttribute("loginUser", userSession);
        return "redirect:/";
    }

    // spring security @GetMapping("/user/logout")
    public String logout(HttpServletRequest req){

        log.info("logout!");
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
