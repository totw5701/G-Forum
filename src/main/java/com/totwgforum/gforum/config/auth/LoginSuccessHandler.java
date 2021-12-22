package com.totwgforum.gforum.config.auth;

import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.dto.user.UserDtoSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("SUCCESS!!!");

        //PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        User user = principal.getUser();

        HttpSession session = request.getSession();
        UserDtoSession userSession = new UserDtoSession();
        userSession.setId(user.getId());
        userSession.setNickName(user.getNickName());
        session.setAttribute("loginUser", userSession);

        response.sendRedirect("/");
    }
}
