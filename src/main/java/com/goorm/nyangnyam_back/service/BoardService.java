package com.goorm.nyangnyam_back.service;

import com.goorm.nyangnyam_back.model.Board;
import com.goorm.nyangnyam_back.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시글 작성
    public Board createBoards(Board board) {
        return boardRepository.save(board);
    }


    // 모든 게시글 가져오기
    public Page<Board> getAllBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }


    // 검색어 제목으로 게시글 가져오기 (게시글 검색)
    public Page<Board> getSearchBoards(String search, Pageable pageable) {
        return boardRepository.findByTitleContainingIgnoreCase(search, pageable);
    }


    // 게시글 상세
    public Board getBoardsById(String id) {
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + id));
    }


    // 게시글 수정
    public Board updateBoards(String id, Board board, String username){
        Board existingBoard = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board Id: " + id));
        // 권한이 유효하지 않은 경우
        if (!existingBoard.getUsername().equals(username)) {
            throw new SecurityException("User is not authorized to update this board");
        }

        existingBoard.setTitle(board.getTitle());
        existingBoard.setContent(board.getContent());
        existingBoard.setPublicRange(board.getPublicRange());
        existingBoard.setCategory(board.getCategory());
        return boardRepository.save(existingBoard);
    }


    // 게시글 삭제
    public void deleteBoards(String id, String username){
        Board existingBoard = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board Id: " + id));
        // 권한이 유효하지 않은 경우
        if (!existingBoard.getUsername().equals(username)) {
            throw new SecurityException("User is not authorized to delete this board");
        }
        boardRepository.deleteById(id);
    }
}
