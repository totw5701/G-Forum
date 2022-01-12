package com.totwgforum.gforum.service;

import com.totwgforum.gforum.domain.Comment;
import com.totwgforum.gforum.dto.comment.CommentDtoRes;
import com.totwgforum.gforum.dto.comment.CommentSaveFormReq;
import com.totwgforum.gforum.repository.CommentRepository;
import com.totwgforum.gforum.repository.PostRepository;
import com.totwgforum.gforum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Long create(CommentSaveFormReq form){

        Comment comment = new Comment();
        comment.setDescription(form.getDescription());
        comment.setAuthor(userRepository.findOne(form.getAuthor()));
        comment.setCreated(LocalDateTime.now());
        comment.setPost(postRepository.findOne(form.getPostId()));

        commentRepository.save(comment);
        return comment.getId();
    }

    public Boolean delete(Long commentId){
        Comment entity = commentRepository.findOne(commentId);
        commentRepository.remove(entity);
        if(entity == null){
            return true;
        }
        return false;
    }

    public List<CommentDtoRes> findAllInPost(Long postId) {
        List<Comment> entities = commentRepository.findAllInPost(postId);

        List<CommentDtoRes> comments = new ArrayList<>();
        for (Comment entity : entities) {
            CommentDtoRes comment = new CommentDtoRes();
            comment.setId(entity.getId());
            comment.setAuthor(entity.getAuthor().getNickName());
            comment.setAuthorId(entity.getAuthor().getId());
            comment.setDescription(entity.getDescription());
            comment.setCreated(entity.getCreated().format(DateTimeFormatter.ofPattern("MM-dd")));

            comments.add(comment);
        }

        return comments;
    }

    public CommentDtoRes findById(Long commentId){
        Comment entity = commentRepository.findOne(commentId);

        CommentDtoRes comment = new CommentDtoRes();
        comment.setId(entity.getId());
        comment.setAuthor(entity.getAuthor().getNickName());
        comment.setAuthorId(entity.getAuthor().getId());
        comment.setDescription(entity.getDescription());
        comment.setCreated(entity.getCreated().format(DateTimeFormatter.ofPattern("MM-dd")));

        return comment;
    }

}
