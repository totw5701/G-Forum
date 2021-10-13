package com.totwgforum.gforum.controller;

import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.dto.PostDtoRes;
import com.totwgforum.gforum.service.PostService;
import com.totwgforum.gforum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/")
    public String home(@RequestParam(value="page", required=false) Integer nowPage, Model model){
        long postCount = postService.findAllCount();
        int pageNum = (int)(postCount/20 + 1);
        if(postCount%20 == 0) pageNum--;
        if(nowPage == null){
            nowPage = pageNum;
        }

        List<Post> listPosts = postService.findPage(pageNum, nowPage);

        List<PostDtoRes> posts = new ArrayList<>();
        for (Post rowPost : listPosts) {
            PostDtoRes post = new PostDtoRes();
            post.setTitle(rowPost.getTitle());
            post.setId(rowPost.getId());
            String date = rowPost.getCreated().format(DateTimeFormatter.ofPattern("MM-dd"));
            post.setDate(date);

            /* User 정보 가져오기.
            String authorNickname = userService.findById(rowPost.getAuthor()).getNickName();
            post.setAuthorNickname(authorNickname);
             */

            post.setAuthorNickname("nickName");
            posts.add(post);
        }

        // 페이징 정보
        int i = (pageNum - nowPage) / 15;
        int pgStart = pageNum -15*i;
        int pgEnd = pageNum -15*(i+1) +1 ;
        if(pgEnd < 1){
            pgEnd = 1;
        }
        boolean isFirstPage = false;
        boolean isLastPage = false;
        if(i == 0){
            isFirstPage = true;
        }
        if(pgEnd == 1){
            isLastPage = true;
        }

        model.addAttribute("posts", posts);
        model.addAttribute("pageNumStart", pgStart);
        model.addAttribute("pageNumEnd", pgEnd);
        model.addAttribute("isFirst", isFirstPage);
        model.addAttribute("isLast", isLastPage);
        model.addAttribute("nowPage", nowPage);
        return "home";
    }
}
