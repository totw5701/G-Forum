package com.totwgforum.gforum.service;

import com.totwgforum.gforum.domain.Comment;
import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.repository.CommentRepository;
import com.totwgforum.gforum.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public Long create(Comment comment){
        commentRepository.save(comment);
        return comment.getId();
    }

    public Boolean delete(Long commentId){
        Comment findOne = commentRepository.findOne(commentId);
        commentRepository.remove(findOne);
        if(findOne == null){
            return true;
        }
        return false;
    }

    public Comment update(Long commentId, Comment newOne){
        // dirty checking
        Comment oldOne = commentRepository.findOne(commentId);
        oldOne.setDescription(newOne.getDescription());
        return oldOne;
    }

    public List<Comment> findAll(){
         return commentRepository.findAll();
    }

    public Comment findById(Long commentId){
        return commentRepository.findOne(commentId);
    }

}
