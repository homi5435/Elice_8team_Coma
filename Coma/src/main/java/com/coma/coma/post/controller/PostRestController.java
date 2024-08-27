package com.coma.coma.post.controller;

import com.coma.coma.common.controller.ImageRestController;
import com.coma.coma.post.dto.PostResponseDto;
import com.coma.coma.post.entity.Post;
import com.coma.coma.post.dto.PostRequestDto;
import com.coma.coma.post.mapper.PostMapper;
import com.coma.coma.post.service.PostSecurityService;
import com.coma.coma.post.service.PostService;
import com.coma.coma.security.CustomUserDetails;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final PostSecurityService postSecurityService;
    private final ImageRestController imageRestController;

    public PostRestController(PostService postService, PostMapper postMapper, PostSecurityService postSecurityService, ImageRestController imageRestController) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.postSecurityService = postSecurityService;
        this.imageRestController = imageRestController;
    }

    // 게시물 생성
    @PostMapping("/{boardId}")
    public ResponseEntity<PostResponseDto> createPost(@PathVariable("boardId") Long boardId,
                                                      @RequestBody PostRequestDto postRequestDto,
                                                      @Nullable @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (customUserDetails == null) {
            // 로그인이 안되었을 경우 401 Unauthorized 반환
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        System.out.println("PostRequestDto 객체에 들어있는 Image URL:" + postRequestDto.getImageUrl());
        Post post = postMapper.toEntity(postRequestDto);
        System.out.println("Post 객체에 들어 있는 image URL: " + post.getImageUrl());

        // 현재 로그인한 유저의 id를 Post 객체에 설정
        post.setUserId(customUserDetails.getUserId());
        // 현재 로그인한 유저의 group id를 Post 객체에 설정
        post.setGroupId(customUserDetails.getGroupId());
        post.setBoardId(boardId);
        PostResponseDto createdDto = postService.createPost(post);

        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    // 게시물 수정
    @PreAuthorize("postSecurityService.isPostOwner(#postId, #customUserDetails.userId)")
    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable("postId") Integer postId,
            @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        PostResponseDto updated = postService.updatePost(postId, postRequestDto);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // 게시물 삭제
    @PreAuthorize("postSecurityService.isPostOwner(#postId, #customUserDetails.userId) or postSecurityService.isAdmin(#customUserDetails.groupId)")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") Integer postId,
                                           @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        // 게시물 조회
        PostResponseDto postResponseDto = postService.findPost(postId);

        if (postResponseDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 게시물의 이미지 URL 가져오기
        String imageUrl = postResponseDto.getImageUrl();

        postService.deletePost(postId);

        if(imageUrl != null && !imageUrl.isEmpty()) {
            imageRestController.deleteImage(imageUrl, customUserDetails);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
