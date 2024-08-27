package com.coma.coma.post.service;

import com.coma.coma.post.entity.Post;
import com.coma.coma.post.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostSecurityService {

    private final PostRepository postRepository;

    private final Integer ADMIN_GROUP_ID = 1;

    public PostSecurityService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public boolean isPostOwner(Integer postId, Integer userId) {
        // 게시물 ID로 게시물 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post Not Found"));

        return post.getUserId().equals(userId);
    }

    // 관리자인지 확인
    public boolean isAdmin(Integer groupId) {
        return groupId.equals(ADMIN_GROUP_ID);
    }
}

