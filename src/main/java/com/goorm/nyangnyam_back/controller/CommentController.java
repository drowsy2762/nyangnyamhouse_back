/*
package com.goorm.nyangnyam_back.controller;


import com.goorm.nyangnyam_back.model.BComment;
import com.goorm.nyangnyam_back.model.Board;
import com.goorm.nyangnyam_back.service.BoardService;
import com.goorm.nyangnyam_back.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private BoardService boardService;

    @PostMapping("/comment/add")
    public String addComment(@RequestParam("boardId") Integer boardId,
                             @RequestParam("content") String content,
                             @RequestParam("author") String author) {
        Board board = boardService.boardView(boardId);
        BComment comment = new BComment();
        comment.setContent(content);
        comment.setAuthor(author);
        comment.setBoard(board);

        commentService.saveComment(comment);

        return "redirect:/board/view?id=" + boardId;
    }

    @GetMapping("/comment/delete")
    public String deleteComment(@RequestParam("id") Integer id,
                                @RequestParam("boardId") Integer boardId) {
        commentService.deleteComment(id);
        return "redirect:/board/view?id=" + boardId;
    }

}
*/
