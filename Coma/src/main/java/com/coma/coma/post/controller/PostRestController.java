package com.coma.coma.post.controller;

import com.coma.coma.post.dto.PostResponseDto;
import com.coma.coma.post.entity.Post;
import com.coma.coma.post.dto.PostRequestDto;
import com.coma.coma.post.mapper.PostMapper;
import com.coma.coma.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService postService;
    private final PostMapper postMapper;

    public PostRestController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    // 게시물 생성
    @PostMapping("/{boardId}")
    public ResponseEntity<PostResponseDto> createPost(@PathVariable("boardId") Long boardId,
                           @RequestBody PostRequestDto postRequestDto) {
        Post post = postMapper.toEntity(postRequestDto);
        // user 정보 임의 설정
        post.setUserId(1);
        post.setGroupId(1);
        post.setBoardId(boardId);
        PostResponseDto createdDto = postService.createPost(post);

        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    // 게시물 수정
    @PutMapping("/{boardId}/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable("boardId") Long boardId,
            @PathVariable("postId") Integer postId,
            @RequestBody PostRequestDto postRequestDto) {

        PostResponseDto updated = postService.updatePost(postId, postRequestDto);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") Integer postId) {

        postService.deletePost(postId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
