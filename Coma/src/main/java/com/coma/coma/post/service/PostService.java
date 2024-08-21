package com.coma.coma.post.service;

import com.coma.coma.post.entity.Post;
import com.coma.coma.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {

    private final PostRepository postRepository;

    // DI
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // create / update 둘 다 여기서 처리
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    // postId로 Post 가져오기
    public Post findPost(int postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException());
    }

    // 특정 게시판의 모든 포스트 가져오기
    public List<Post> findAll(Long boardId) {
        return postRepository.findAll(boardId);
    }

    // Id 이용해서 Post 삭제
    public void deletePost(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException());

        postRepository.delete(post);
    }
}
