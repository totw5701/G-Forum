package com.totwgforum.gforum.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = {"/", "/user/create", "/user/login", "/posts/*", "/css/*"};
    private static final String[] blackList = {"/posts/update/*", "/posts/delete", "/posts/create"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String reqURI = req.getRequestURI();

        try {
            log.info("loginFilter START = {}", reqURI);
            if (isWhiteListPath(reqURI)) {
                log.info("인증 체크 로직 실행 {}", reqURI);
                HttpSession session = req.getSession(false);
                if (session == null || session.getAttribute("loginUser") == null) {
                    log.info("미인증 사용자 요청 {}", reqURI);
                    res.sendRedirect("/user/login");
                    return;
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("loginFilter END = {}", reqURI);
        }
    }

    private boolean isWhiteListPath(String reqURI) {
        if (PatternMatchUtils.simpleMatch(blackList, reqURI)) {
            log.info("blackList={}", reqURI);
            return true;
        }
        return !PatternMatchUtils.simpleMatch(whiteList, reqURI);
    }
}
