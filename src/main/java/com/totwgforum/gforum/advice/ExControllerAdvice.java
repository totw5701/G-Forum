package com.totwgforum.gforum.advice;

import com.totwgforum.gforum.advice.exception.CAuthorNotMatchedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExControllerAdvice {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public String exHandler(Model model, CAuthorNotMatchedException e, HttpServletRequest req) {
        log.error("[exceptionHandler} ex", e);

        Locale locale = req.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("errors", locale);

        String msg = (String) bundle.getObject("authorNotMatched");

        model.addAttribute("msg", msg);

        return "/error/exception";
    }
}
