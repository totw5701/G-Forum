package com.totwgforum.gforum.controller;

import com.totwgforum.gforum.domain.Comment;
import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.dto.comment.CommentSaveFormReq;
import com.totwgforum.gforum.service.CommentService;
import com.totwgforum.gforum.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/comment/create")
    public String createCommentProcess(@Validated @ModelAttribute("comment") CommentSaveFormReq form, BindingResult bindingResult,
                                       @SessionAttribute(name = "loginUser", required = false) User loginUser,
                                       Model model) {

        if (loginUser == null) {
            log.info("logfinUser = {}", loginUser);
            return "redirect:/posts/" + form.getPostId();
        }

        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return "redirect:/posts/" + form.getPostId();
        }

        Post post = postService.findById(form.getPostId());

        Comment comment = new Comment();
        comment.setAuthor(form.getAuthor());
        comment.setDescription(form.getDescription());
        comment.setCreated(LocalDateTime.now());
        comment.setPost(post);
        commentService.create(comment);

        post.getComments().add(comment);

        return "redirect:/posts/" + form.getPostId();
    }

    @PostMapping("/comment/delete")
    public String deleteComment(@RequestParam("id") Long commentId,
                                @RequestParam("postId") Long postId,
                                @SessionAttribute(name = "loginUser", required = false) User loginUser) {

        Comment comment = commentService.findById(commentId);

        if (loginUser == null) {
            return "redirect:/posts/" + postId;
        }

        if (!loginUser.getId().equals(comment.getAuthor())) {
            return "redirect:/posts/" + postId;
        }

        commentService.delete(commentId);
        return "redirect:/posts/" + postId;
    }
}
