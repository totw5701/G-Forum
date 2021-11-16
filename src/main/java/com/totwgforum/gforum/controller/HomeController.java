package com.totwgforum.gforum.controller;

import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.dto.PagingDto;
import com.totwgforum.gforum.dto.post.PostDtoRes;
import com.totwgforum.gforum.service.PostService;
import com.totwgforum.gforum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/")
    public String home(@RequestParam(value="page", required=false) Integer nowPage,
                       Model model,
                       HttpServletRequest req,
                       @SessionAttribute(name = "loginUser", required = false) User loginUser){

        model.addAttribute("loginUser", loginUser);

        // 페이징
        long postCount = postService.findAllCount();
        int pageNum = (int)(postCount/20 + 1);
        if(postCount%20 == 0) pageNum--;
        if(nowPage == null){
            nowPage = pageNum;
        }
        PagingDto pagingDto = new PagingDto(pageNum, nowPage);

        // post -> postDtoRes
        List<Post> listPosts = postService.findPage(pageNum, nowPage);
        List<PostDtoRes> posts = new ArrayList<>();
        for (Post rowPost : listPosts) {
            PostDtoRes post = new PostDtoRes();
            post.setTitle(rowPost.getTitle());
            post.setId(rowPost.getId());
            String date = rowPost.getCreated().format(DateTimeFormatter.ofPattern("MM-dd"));
            post.setDate(date);
            String authorNickname = userService.findById(rowPost.getUser().getId()).getNickName();
            post.setAuthorNickname(authorNickname);

            posts.add(post);
        }

        model.addAttribute("posts", posts);
        model.addAttribute("pagingDto", pagingDto);
        return "home";
    }

    @GetMapping("/search")
    public String home(@RequestParam(value="searchPost", required=true) String keyword,
                        @RequestParam(value="page", required=false) Integer nowPage,
                       Model model,
                       HttpServletRequest req,
                       @SessionAttribute(name = "loginUser", required = false) User loginUser){

        model.addAttribute("loginUser", loginUser);



        // 페이징
        long postCount = postService.findAllCountSearch(keyword);
        int pageNum = (int)(postCount/20 + 1);
        if(postCount%20 == 0) pageNum--;
        if(nowPage == null){
            nowPage = pageNum;
        }
        PagingDto pagingDto = new PagingDto(pageNum, nowPage);

        // post -> postDtoRes
        List<Post> listPosts = postService.findPageSearch(pageNum, nowPage, keyword);
        List<PostDtoRes> posts = new ArrayList<>();
        for (Post rowPost : listPosts) {
            PostDtoRes post = new PostDtoRes();
            post.setTitle(rowPost.getTitle());
            post.setId(rowPost.getId());
            String date = rowPost.getCreated().format(DateTimeFormatter.ofPattern("MM-dd"));
            post.setDate(date);
            String authorNickname = userService.findById(rowPost.getUser().getId()).getNickName();
            post.setAuthorNickname(authorNickname);

            posts.add(post);
        }

        model.addAttribute("posts", posts);
        model.addAttribute("pagingDto", pagingDto);
        model.addAttribute("keyword", keyword);
        return "search";
    }

}
