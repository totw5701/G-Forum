package com.totwgforum.gforum.config.auth;

import com.totwgforum.gforum.GForumApplication;
import org.aspectj.bridge.MessageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.Thymeleaf;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

@Service("LoginFailureHandler")
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMsg = "";

        if (exception instanceof BadCredentialsException) { // PWD불일치
            errorMsg = ResourceBundle.getBundle("errors").getString("passwordNotMatch");
        } else if (exception instanceof InternalAuthenticationServiceException) { //존재하지 않는
            errorMsg = ResourceBundle.getBundle("errors").getString("nonUserEmail");
        } else {
            errorMsg = ResourceBundle.getBundle("errors").getString("loginFailure");
        }

        String email = request.getParameter("email");
        request.setAttribute("email", email);
        request.setAttribute("error", errorMsg);

        request.getRequestDispatcher("/login-error").forward(request, response);
    }

}
