package com.totwgforum.gforum.controller;

import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.dto.PostDtoRes;
import com.totwgforum.gforum.service.PostService;
import com.totwgforum.gforum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/")
    public String home(Model model){
        List<Post> listPosts = postService.listUp();

        List<PostDtoRes> posts = new ArrayList<>();
        for (Post rowPost : listPosts) {
            PostDtoRes post = new PostDtoRes();
            post.setTitle(rowPost.getTitle());
            post.setId(rowPost.getId());
            String date = rowPost.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            post.setDate(date);

            /* User 정보 가져오기.
            String authorNickname = userService.findById(rowPost.getAuthor()).getNickName();
            post.setAuthorNickname(authorNickname);
             */

            post.setAuthorNickname("nickName");
            posts.add(post);
        }
        model.addAttribute("posts", posts);
        return "home";
    }
}
