package com.totwgforum.gforum.controller;

import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.dto.PagingDto;
import com.totwgforum.gforum.dto.post.PostDtoRes;
import com.totwgforum.gforum.dto.user.UserDtoSession;
import com.totwgforum.gforum.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String home(@RequestParam(value="page", required=false) Integer nowPage,
                       Model model,
                       HttpServletRequest req,
                       @SessionAttribute(name = "loginUser", required = false) UserDtoSession loginUser){

        model.addAttribute("loginUser", loginUser);

        // 페이징
        long postCount = postService.findAllCount();
        int pageNum = (int)(postCount/20 + 1);
        if(postCount%20 == 0) pageNum--;
        if(nowPage == null){
            nowPage = pageNum;
        }
        PagingDto pagingDto = new PagingDto(pageNum, nowPage);


        List<PostDtoRes> posts = postService.findPage(pageNum, nowPage);

        model.addAttribute("posts", posts);
        model.addAttribute("pagingDto", pagingDto);
        return "home";
    }

    @GetMapping("/search")
    public String home(@RequestParam(value="searchPost", required=true) String keyword,
                        @RequestParam(value="page", required=false) Integer nowPage,
                       Model model,
                       HttpServletRequest req,
                       @SessionAttribute(name = "loginUser", required = false) UserDtoSession loginUser){

        model.addAttribute("loginUser", loginUser);



        // 페이징
        long postCount = postService.findAllCountSearch(keyword);
        int pageNum = (int)(postCount/20 + 1);
        if(postCount%20 == 0) pageNum--;
        if(nowPage == null){
            nowPage = pageNum;
        }
        PagingDto pagingDto = new PagingDto(pageNum, nowPage);

        List<PostDtoRes> posts = postService.findPageSearch(pageNum, nowPage, keyword);

        model.addAttribute("posts", posts);
        model.addAttribute("pagingDto", pagingDto);
        model.addAttribute("keyword", keyword);
        return "search";
    }

}
