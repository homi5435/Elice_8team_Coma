package com.coma.coma.post.service;

import com.coma.coma.post.entity.Post;
import com.coma.coma.post.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostSecurityService {

    private final PostRepository postRepository;

    public PostSecurityService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public boolean isPostOwner(Integer postId, Integer userId) {
        // 게시물 ID로 게시물 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post Not Found"));

        return post.getUserId().equals(userId);
    }
}

