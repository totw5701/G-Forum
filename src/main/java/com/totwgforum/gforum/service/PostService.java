package com.totwgforum.gforum.service;

import com.totwgforum.gforum.domain.Post;
import com.totwgforum.gforum.dto.PagingDto;
import com.totwgforum.gforum.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public Long create(Post post){
        postRepository.save(post);
        return post.getId();
    }

    public Boolean delete(Long postId){
        Post findOne = postRepository.findOne(postId);
        postRepository.remove(findOne);
        if(findOne == null){
            return true;
        }
        return false;
    }

    public Post update(Long postId, Post newOne){
        // dirty checking
        Post oldOne = postRepository.findOne(postId);
        oldOne.setTitle(newOne.getTitle());
        oldOne.setDescription(newOne.getDescription());
        return oldOne;
    }

    public List<Post> findAll(){
         return postRepository.findAll();
    }

    public long findAllCount(){
        Object count = postRepository.findCount();
        return (long)count;
    }

    public long findAllCountSearch(String keyword){
        Object count = postRepository.findCountSearch(keyword);
        return (long)count;
    }

    public List<Post> findPage(int pageNum, int nowPage){
        int reverseNowPage = pageNum - nowPage;
        List<Post> findList = postRepository.findList(reverseNowPage);
        return findList;
    }

    public List<Post> findPageSearch(int pageNum, int nowPage, String keyword){
        int reverseNowPage = pageNum - nowPage;
        List<Post> findList = postRepository.findListSearch(reverseNowPage, keyword);
        return findList;
    }

    public Post findById(Long postId){
        return postRepository.findOne(postId);
    }

}
