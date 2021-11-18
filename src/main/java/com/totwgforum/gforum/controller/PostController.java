package com.totwgforum.gforum.controller;

import com.totwgforum.gforum.domain.Comment;
import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.dto.comment.CommentDtoRes;
import com.totwgforum.gforum.dto.post.PostDtoRes;
import com.totwgforum.gforum.dto.post.PostSaveFormReq;
import com.totwgforum.gforum.dto.post.PostUpdateFormReq;
import com.totwgforum.gforum.service.CommentService;
import com.totwgforum.gforum.service.PostService;
import com.totwgforum.gforum.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    @GetMapping("/posts/create")
    public String createPostForm(Model model, @SessionAttribute(name = "loginUser", required = false) User loginUser){
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("post", new PostSaveFormReq());
        return "post/create";
    }

    @PostMapping("/posts/create")
    public String createPostProcess(@Validated @ModelAttribute("post") PostSaveFormReq form, BindingResult bindingResult,
                                    @SessionAttribute(name = "loginUser", required = false) User loginUser,
                                    Model model){

        model.addAttribute("loginUser", loginUser);

        if(bindingResult.hasErrors()){
            log.info("error={}", bindingResult);
            return "post/create";
        }

        Long postId = postService.create(form);

        return "redirect:/posts/"+postId;
    }

    @GetMapping("/posts/{postId}")
    public String detailPost(@PathVariable Long postId, Model model, @SessionAttribute(name = "loginUser", required = false) User loginUser){

        model.addAttribute("loginUser", loginUser);
        model.addAttribute("comment", new Comment());

        PostDtoRes post = postService.findById(postId);

        if (loginUser != null) {
            // 세션 유저와 author가 일치하는지 확인하여 boolean값을 렌더링.
            if (loginUser.getId().equals(post.getAuthor())) {
                model.addAttribute("isAuthorLogin", true);
            } else {
                model.addAttribute("isAuthorLogin", false);
            }
        }
        model.addAttribute("post", post);

        List<CommentDtoRes> comments = commentService.findAllInPost(post.getId());

        model.addAttribute("comments", comments);

        return "post/detail";
    }

    @GetMapping("/posts/update/{postId}")
    public String updatePostForm(@PathVariable Long postId, Model model, @SessionAttribute(name = "loginUser", required = false) User loginUser){

        model.addAttribute("loginUser", loginUser);

        PostDtoRes post = postService.findById(postId);

        // author와 로그인 한 사용자가 일치하는지 확인
        if (!loginUser.getId().equals(post.getAuthor())) {
            log.info("post/update, 세션과 author가 다름");
            return "redirect:/";
        }


        model.addAttribute("post", post);
        return "post/update";
    }

    @PostMapping("/posts/update")
    public String updatePostProcess(@Validated @ModelAttribute("post") PostUpdateFormReq form, BindingResult bindingResult,
                                    @SessionAttribute(name = "loginUser", required = false) User loginUser){

        // author와 로그인 한 사용자가 일치하는지 확인
        if (!loginUser.getId().equals(form.getAuthor())) {
            log.info("post/update, 세션과 author가 다름");
            return "redirect:/";
        }

        if(bindingResult.hasErrors()){
            log.info("error={}", bindingResult);
            return "post/update";
        }

        postService.update(form.getId(), form);
        return "redirect:/posts/" + form.getId();
    }

    @PostMapping("/posts/delete")
    public String deletePost(@RequestParam("id") Long postId,
                             @RequestParam("author") Long author,
                             @SessionAttribute(name = "loginUser", required = false) User loginUser){

        // 로그인된 회원과 authorId 일치 확인.
        if (!loginUser.getId().equals(author)) {
            return "redirect:/";
        }

        postService.delete(postId);
        return "redirect:/";
    }
}
