package com.coma.coma.post.controller;

import com.coma.coma.board.entity.Board;
import com.coma.coma.board.service.BoardService;
import com.coma.coma.comments.Entity.Comment;
import com.coma.coma.comments.Service.CommentService;
import com.coma.coma.post.entity.Post;
import com.coma.coma.post.entity.PostRequestDto;
import com.coma.coma.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;


@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final BoardService boardService;
    private final CommentService commentService;

    public PostController(PostService postService, BoardService boardService, CommentService commentService) {
        this.postService = postService;
        this.boardService = boardService;
        this.commentService = commentService;
    }

    // 게시물 목록 페이지
    @GetMapping("/list/{boardId}")
    public String getPosts(@PathVariable("boardId") Long boardId, Model model) {
        Board board = boardService.findOne(boardId);
        model.addAttribute("posts", postService.findAll(boardId));
        model.addAttribute("board", board);
        return "board/board";
    }

    // 게시글 상세 페이지
    @GetMapping("/{postId}")
    public String getPost(@PathVariable("postId") Long postId, Model model) {
        Post post = postService.findPost(postId);
        List<Comment> comments = commentService.getCommentsByPostId(Math.toIntExact(postId));
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "post/post";
    }

    // 새 게시물 작성 페이지 이동
    @GetMapping("/new/{boardId}")
    public String newPostPage(@PathVariable("boardId") Long boardId, Model model) {
        model.addAttribute("boardId", boardId);
        return "post/createPost";
    }

    // 게시물 생성 요청
    @PostMapping("/create/{boardId}")
    public String createPost( @PathVariable("boardId") Long boardId,@ModelAttribute PostRequestDto postRequestDto) {
        // TODO: Mapper 이용해서 처리
        // TODO: User, Group 관련 처리 필요
        Post post = new Post();
        post.setUserId(1L);
        post.setGroupId(1L);
        post.setBoardId(postRequestDto.getBoardId());
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());

        postService.createPost(post);
        return "redirect:/posts/list/" + boardId;
    }

    // 게시물 수정 페이지 이동
    @GetMapping("/{postId}/edit")
    public String editPostPage(@PathVariable("postId") Long postId, Model model) {
        Post post = postService.findPost(postId);
        model.addAttribute("post", post);
        return "post/editPost";
    }

    // 게시물 수정 요청
    @PostMapping("/{postId}/update")
    public String updatePost(@PathVariable Long postId, @ModelAttribute PostRequestDto postRequestDto) {
        Post post = postService.findPost(postId);
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());

        postService.createPost(post);
        return "redirect:/posts/" + postId;
    }

    // 게시물 삭제 요청
    @GetMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId) {
        Post post = postService.findPost(postId);
        Long boardId = post.getBoardId();
        postService.deletePost(postId);
        return "redirect:/posts/list/" + boardId;
    }
}
