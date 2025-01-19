package com.goorm.nyangnyam_back.controller;

import com.goorm.nyangnyam_back.jwt.JWTUtil;
import com.goorm.nyangnyam_back.service.BoardService;
import com.goorm.nyangnyam_back.model.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final JWTUtil jwtUtil;

    
    // 게시글 작성 POST
    @PostMapping("/boards")
    public Board createBoards(@RequestBody Board board,
                              @RequestHeader("access") String accessToken){
        // access 토큰에서 사용자 이름 추출
        String username = jwtUtil.getUsername(accessToken);
        board.setUsername(username);

        return boardService.createBoards(board);
    }


    // 게시글 목록 GET
    @GetMapping("/boards")
    public ResponseEntity<Map<String, Object>> getAllBoards( // page: 페이지 번호 (0부터 시작), size: 한 페이지에 포함될 데이터 개수, sort: 정렬 기준)
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "search", required = false) String search) {

        Page<Board> boardPage; // Page는 페이징 관련 메타데이터(현재 페이지, 총 페이지 수, 총 데이터 수 등)
        if (search == null || search.isEmpty()) {
            boardPage = boardService.getAllBoards(pageable); // Pageable 인터페이스: 페이징과 정렬 정보를 담고 있는 객체
        } else {
            boardPage = boardService.getSearchBoards(search, pageable);
        }
        // Create a response map
        Map<String, Object> response = new HashMap<>();
        response.put("content", boardPage.getContent()); // 게시글 데이터
        response.put("currentPage", boardPage.getNumber()); // 현재 페이지
        response.put("totalPages", boardPage.getTotalPages()); // 총 페이지 수
        response.put("pageSize", boardPage.getSize()); // 한 페이지당 게시글 수
        response.put("totalElements", boardPage.getTotalElements()); // 전체 게시글

        // Custom pagination info
        int currentPage = boardPage.getNumber();      // 현재 페이지
        int startPage = Math.max(currentPage-4, 0);   // 시작 페이지
        int endPage = Math.min(currentPage+5, boardPage.getTotalPages()-1); // 끝 페이지
        response.put("startPage", startPage);
        response.put("endPage", endPage);

        return ResponseEntity.ok(response);
    }


   // 게시글 상세 GET
   @GetMapping("/boards/{id}")
    public ResponseEntity<Board> getBoardsById(@PathVariable String id){
       Board board = boardService.getBoardsById(id);
       if(board != null){
           return ResponseEntity.ok(board);
       }
       else{
           return ResponseEntity.notFound().build();
       }
    }


    // 게시글 수정 PUT
    @PutMapping("/boards/{id}")
    public ResponseEntity<Board> updateBoards(@PathVariable String id,
                                              @RequestBody Board board,
                                              @RequestHeader("access") String accessToken){
        try {
            String username = jwtUtil.getUsername(accessToken);
            Board updatedBoard = boardService.updateBoards(id, board, username);
            return ResponseEntity.ok(updatedBoard);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (SecurityException e) {
            //System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }


    // 게시글 삭제 DELETE
    @DeleteMapping("/boards/{id}")
    public ResponseEntity<String> deleteBoards(@PathVariable String id,
                                               @RequestHeader("access") String accessToken){
        try {
            String username = jwtUtil.getUsername(accessToken);
            boardService.deleteBoards(id, username);
            return ResponseEntity.ok("Board deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }
}
