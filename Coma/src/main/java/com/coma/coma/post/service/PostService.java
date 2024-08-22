package com.coma.coma.post.service;

import com.coma.coma.post.dto.PostRequestDto;
import com.coma.coma.post.dto.PostResponseDto;
import com.coma.coma.post.entity.Post;
import com.coma.coma.post.mapper.PostMapper;
import com.coma.coma.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    // DI
    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    // create
    public PostResponseDto createPost(Post post) {
        Post saved = postRepository.save(post);
        PostResponseDto postResponseDto = postMapper.toResponseDto(saved);
        return postResponseDto;
    }

    // update
    public PostResponseDto updatePost(Integer postId, PostRequestDto postRequestDto) {
        Post targetPost = postRepository.findById(postId).orElseThrow(NoSuchElementException::new);
        targetPost.setTitle(postRequestDto.getTitle());
        targetPost.setContent(postRequestDto.getContent());
        postRepository.save(targetPost);

        return postMapper.toResponseDto(targetPost);
    }

    // postId로 Post 가져오기
    public PostResponseDto findPost(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException());
        PostResponseDto postResponseDto = postMapper.toResponseDto(post);
        return postResponseDto;
    }

    // 특정 게시판의 모든 포스트 가져오기
    public List<PostResponseDto> findAll(Long boardId) {

        List<Post> posts = postRepository.findAll(boardId);
        List<PostResponseDto> postResponseDtos = posts.stream()
                .map(post -> postMapper.toResponseDto(post))
                .collect(Collectors.toList());
        return postResponseDtos;
    }

    // Id 이용해서 Post 삭제
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException());
        postRepository.delete(post);
    }

    public List<PostResponseDto> findByKeyword(Long boardId, String keyword) {
        List<Post> posts = postRepository.findByKeyword(boardId, keyword);
        List<PostResponseDto> postResponseDtos = posts.stream()
                .map(post -> postMapper.toResponseDto(post))
                .collect(Collectors.toList());
        return postResponseDtos;
    }
}
