package com.coma.coma.post.controller;

import com.coma.coma.board.entity.Board;
import com.coma.coma.board.service.BoardService;
import com.coma.coma.comments.Entity.Comment;
import com.coma.coma.comments.Service.CommentService;
import com.coma.coma.post.dto.Page;
import com.coma.coma.post.dto.PostResponseDto;
import com.coma.coma.post.service.PostService;
import com.coma.coma.security.CustomUserDetails;
import com.coma.coma.users.dto.UserResponseDto;
import com.coma.coma.users.service.UserService;
import jakarta.annotation.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;


@Controller
@RequestMapping("/page/posts")
public class PostController {

    private final PostService postService;
    private final BoardService boardService;
    private final CommentService commentService;
    private final UserService userService;

    public PostController(PostService postService, BoardService boardService, CommentService commentService, UserService userService) {
        this.postService = postService;
        this.boardService = boardService;
        this.commentService = commentService;
        this.userService = userService;
    }

    // 게시물 목록 페이지 - 페이지네이션 적용
    @GetMapping("/list/{boardId}")
    public String getPosts(@PathVariable("boardId") Long boardId,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "10") int size,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           @Nullable @AuthenticationPrincipal CustomUserDetails customUserDetails,      // 로그인된 user 정보 가져오기
                           Model model) {
        Page<PostResponseDto> posts;
        boolean isAuthenticated = customUserDetails != null;

        if (keyword != null && !keyword.isEmpty()) {
            posts = postService.findByKeywordWithPagination(boardId, keyword, page, size);
        } else {
            posts = postService.getPostsWithPagination(boardId, page, size);
        }
        Board board = boardService.findOne(boardId);
        model.addAttribute("posts", posts);
        model.addAttribute("board", board);
        model.addAttribute("keyword", keyword);
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "board/board";
    }

    // 게시글 상세 페이지
    @GetMapping("/{postId}")
    public String getPost(@PathVariable("postId") Integer postId, Model model,
                          @Nullable @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        PostResponseDto postResponseDto = postService.findPost(postId);
        // 글 작성자의 정보를 받아와 저장
        UserResponseDto authorInfo = userService.getUserByUserId(postResponseDto.getUserId());

        boolean isAuthor = false;   // 로그인 한 유저와 글 작성자가 일치하는지 확인
        boolean isAdmin = false;    // 관리자 계정인지 확인

        if(customUserDetails != null) {
            isAuthor = customUserDetails.getUserId() == authorInfo.getUserId();
            isAdmin = customUserDetails.getGroupId() == 1;  // group id가 1인 경우 -> 관리자인 경우 글 삭제 가능하도록
        }

        List<Comment> comments = commentService.getCommentsByPostId(postId);


        model.addAttribute("post", postResponseDto);
        model.addAttribute("comments", comments);
        model.addAttribute("isAuthor", isAuthor);
        model.addAttribute("authorInfo", authorInfo);       // 글 작성자 정보 전달
        model.addAttribute("isAdmin", isAdmin);             // 관리자 계정 확인
        return "post/post";
    }

    // 새 게시물 작성 페이지 이동
    @GetMapping("/new/{boardId}")
    public String newPostPage(@PathVariable("boardId") Long boardId, Model model) {
        model.addAttribute("boardId", boardId);
        return "post/createPost";
    }


    // 게시물 수정 페이지 이동
    @GetMapping("/edit/{postId}")
    public String editPostPage(@PathVariable("postId") Integer postId, Model model) {
        PostResponseDto post = postService.findPost(postId);
        model.addAttribute("post", post);
        return "post/editPost";
    }

}
