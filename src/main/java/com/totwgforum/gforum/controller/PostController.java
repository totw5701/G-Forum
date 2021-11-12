package com.totwgforum.gforum.controller;

import com.totwgforum.gforum.domain.Comment;
import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.dto.comment.CommentDtoRes;
import com.totwgforum.gforum.dto.comment.CommentSaveFormReq;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

        Post post = new Post();
        post.setCreated(LocalDateTime.now());
        post.setAuthor(form.getAuthor());
        post.setTitle(form.getTitle());
        post.setDescription(form.getDescription());
        postService.create(post);
        return "redirect:/posts/"+post.getId();
    }

    @GetMapping("/posts/{postId}")
    public String detailPost(@PathVariable Long postId, Model model, @SessionAttribute(name = "loginUser", required = false) User loginUser){

        model.addAttribute("loginUser", loginUser);
        model.addAttribute("comment", new Comment());

        Post rowPost = postService.findById(postId);

        if (loginUser != null) {
            // 세션 유저와 author가 일치하는지 확인하여 boolean값을 렌더링.
            if (loginUser.getId().equals(rowPost.getAuthor())) {
                model.addAttribute("isAuthorLogin", true);
            } else {
                model.addAttribute("isAuthorLogin", false);
            }
        }


        PostDtoRes post = new PostDtoRes();
        post.setTitle(rowPost.getTitle());
        post.setDescription(rowPost.getDescription());
        post.setId(rowPost.getId());
        String date = rowPost.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
        post.setDate(date);

        User author = userService.findById(rowPost.getAuthor());
        post.setAuthorNickname(author.getNickName());
        post.setAuthor(author.getId());

        model.addAttribute("post", post);

        List<Comment> rowComments = commentService.findAllInPost(rowPost.getId());
        System.out.println("rowComments = " + rowComments);
        List<CommentDtoRes> comments = new ArrayList<>();
        for (Comment c : rowComments) {
            CommentDtoRes comment = new CommentDtoRes();
            comment.setId(c.getId());
            comment.setDescription(c.getDescription());
            comment.setCreated(c.getCreated().format(DateTimeFormatter.ofPattern("MM-dd")));

            User commentAuthor = userService.findById(c.getAuthor());
            comment.setAuthor(commentAuthor.getNickName());
            comment.setAuthorId(c.getAuthor());

            comments.add(comment);
        }

        model.addAttribute("comments", comments);

        return "post/detail";
    }

    @GetMapping("/posts/update/{postId}")
    public String updatePostForm(@PathVariable Long postId, Model model, @SessionAttribute(name = "loginUser", required = false) User loginUser){

        model.addAttribute("loginUser", loginUser);

        Post rowPost = postService.findById(postId);

        // author와 로그인 한 사용자가 일치하는지 확인
        if (!loginUser.getId().equals(rowPost.getAuthor())) {
            log.info("post/update, 세션과 author가 다름");
            return "redirect:/";
        }

        PostDtoRes post = new PostDtoRes();
        post.setId(rowPost.getId());
        post.setAuthor(rowPost.getAuthor());
        post.setTitle(rowPost.getTitle());
        post.setDescription(rowPost.getDescription());

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

        Post post = new Post();
        post.setTitle(form.getTitle());
        post.setDescription(form.getDescription());
        postService.update(form.getId(), post);
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
