package com.coma.coma.post.controller;

import com.coma.coma.board.entity.Board;
import com.coma.coma.board.service.BoardService;
import com.coma.coma.comments.Entity.Comment;
import com.coma.coma.comments.Service.CommentService;
import com.coma.coma.post.dto.PostResponseDto;
import com.coma.coma.post.entity.Post;
import com.coma.coma.post.service.PostService;
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
    public String getPost(@PathVariable("postId") Integer postId, Model model) {
        PostResponseDto postResponseDto = postService.findPost(postId);
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        model.addAttribute("post", postResponseDto);
        model.addAttribute("comments", comments);
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
        Long boardId = post.getBoardId();
        model.addAttribute("post", post);
        return "post/editPost";
    }

}
