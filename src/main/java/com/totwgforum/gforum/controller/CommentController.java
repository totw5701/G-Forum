package com.totwgforum.gforum.controller;

import com.totwgforum.gforum.advice.exception.CAuthorNotMatchedException;
import com.totwgforum.gforum.domain.User;
import com.totwgforum.gforum.dto.comment.CommentDtoRes;
import com.totwgforum.gforum.dto.comment.CommentSaveFormReq;
import com.totwgforum.gforum.dto.user.UserDtoSession;
import com.totwgforum.gforum.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/create")
    public String createCommentProcess(@Validated @ModelAttribute("comment") CommentSaveFormReq form, BindingResult bindingResult,
                                       @SessionAttribute(name = "loginUser", required = false) UserDtoSession loginUser,
                                       Model model) {

        if (loginUser == null) {
            log.info("logfinUser = {}", loginUser);
            return "redirect:/posts/" + form.getPostId();
        }

        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return "redirect:/posts/" + form.getPostId();
        }

        if(form.getAuthor() != loginUser.getId()){
            throw new CAuthorNotMatchedException();
        }

        commentService.create(form);

        return "redirect:/posts/" + form.getPostId();
    }

    @PostMapping("/comment/delete")
    public String deleteComment(@RequestParam("id") Long commentId,
                                @RequestParam("postId") Long postId,
                                @SessionAttribute(name = "loginUser", required = false) UserDtoSession loginUser) {

        CommentDtoRes comment = commentService.findById(commentId);

        if (loginUser == null) {
            return "redirect:/posts/" + postId;
        }

        if (!loginUser.getId().equals(comment.getAuthorId())) {
            throw new CAuthorNotMatchedException();
        }

        commentService.delete(commentId);
        return "redirect:/posts/" + postId;
    }
}
