package com.coma.coma.post.controller;

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

    public PostRestController(PostService postService, PostMapper postMapper, PostSecurityService postSecurityService) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.postSecurityService = postSecurityService;
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
        Post post = postMapper.toEntity(postRequestDto);
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

        postService.deletePost(postId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
