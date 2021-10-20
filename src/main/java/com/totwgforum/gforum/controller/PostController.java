package com.totwgforum.gforum.controller;

import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.dto.post.PostDtoRes;
import com.totwgforum.gforum.dto.post.PostSaveFormReq;
import com.totwgforum.gforum.dto.post.PostUpdateFormReq;
import com.totwgforum.gforum.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/create")
    public String createPostForm(Model model){
        model.addAttribute("post", new Post());
        return "create";
    }

    @PostMapping("/posts/create")
    public String createPostProcess(@Validated @ModelAttribute("post") PostSaveFormReq form, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("error={}", bindingResult);
            return "create";
        }

        Post post = new Post();
        post.setCreated(LocalDateTime.now());
        post.setAuthor(form.getAuthor());
        post.setTitle(form.getTitle());
        post.setDescription(form.getDescription());
        postService.create(post);
        return "redirect:/";
    }

    @GetMapping("/posts/{postId}")
    public String detailPost(@PathVariable Long postId, Model model){
        Post rowPost = postService.findById(postId);
        PostDtoRes post = new PostDtoRes();
        post.setTitle(rowPost.getTitle());
        post.setDescription(rowPost.getDescription());
        post.setId(rowPost.getId());
        String date = rowPost.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
        post.setDate(date);

            /* User 정보 가져오기.
            String authorNickname = userService.findById(rowPost.getAuthor()).getNickName();
            post.setAuthorNickname(authorNickname);
             */
            post.setAuthor(1L);
            post.setAuthorNickname("nickName");

        model.addAttribute("post", post);
        return "detail";
    }

    @GetMapping("/posts/update/{postId}")
    public String updatePostForm(@PathVariable Long postId, Model model){

        // author와 로그인 한 사용자가 일치하는지 확인

        Post rowPost = postService.findById(postId);
        PostDtoRes post = new PostDtoRes();
        post.setId(rowPost.getId());
        post.setAuthor(rowPost.getId());
        post.setTitle(rowPost.getTitle());
        post.setDescription(rowPost.getDescription());
        model.addAttribute("post", post);
        return "update";
    }

    @PostMapping("/posts/update")
    public String updatePostProcess(@Validated @ModelAttribute("post") PostUpdateFormReq form, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("error={}", bindingResult);
            return "update";
        }

        Post post = new Post();
        post.setTitle(form.getTitle());
        post.setDescription(form.getDescription());
        postService.update(form.getId(), post);
        return "redirect:/posts/" + form.getId();
    }

    @PostMapping("/posts/delete")
    public String deletePost(@RequestParam("id") Long postId,
                             @RequestParam("author") Long author){

        // 로그인된 회원과 authorId 일치 확인.

        System.out.println("PostController.deletePost");
        postService.delete(postId);
        return "redirect:/";
    }
}
