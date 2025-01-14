package com.goorm.nyangnyam_back.controller;

import com.goorm.nyangnyam_back.model.DComment;
import com.goorm.nyangnyam_back.model.Diaries;
import com.goorm.nyangnyam_back.service.DiariesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// RESTAPI 세팅 부분입니다 (GET, POST, PUT, DELETE)

@RestController
@RequestMapping("/diaries")
public class DiariesController {

    // 의존성 주입
    @Autowired
    private DiariesService diariesService;

    // 글목록 GET
    @GetMapping
    public List<Diaries> getAllDiaries(){
        return diariesService.getAllDiaries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Diaries> getDiariesById(@PathVariable String id){
        Diaries diaries = diariesService.getDiariesById(id);
        if(diaries != null){
            return ResponseEntity.ok(diaries);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    // 글작성 POST
    @PostMapping
    public Diaries createDiaries(@RequestBody Diaries diaries) {
        return diariesService.createDiaries(diaries);
    }

    // 글수정 PUT
    @PutMapping("/{id}")
    public ResponseEntity<Diaries> updateDiaries(@PathVariable String id, @RequestBody Diaries diaries) {
        Diaries updatedDiaries = diariesService.updateDiaries(id, diaries);
        if (updatedDiaries != null) {
            return ResponseEntity.ok(updatedDiaries);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 글삭제 DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiaries(@PathVariable String id) {
        diariesService.deleteDiaries(id);
        return ResponseEntity.noContent().build();
    }

    // 좋아요 기능
    @PostMapping("/{id}/like")
    public ResponseEntity<Diaries> likeDiaries(@PathVariable String id) {
        Diaries likedDiaries = diariesService.likeDiaries(id);
        if (likedDiaries != null) {
            return ResponseEntity.ok(likedDiaries);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 카테고리별 글목록
    @PostMapping("/category")
    public ResponseEntity<Diaries> postDiariesSortedByCategory(@PathVariable String category){
        Diaries diaries = diariesService.getDiariesSoretedByCategory(category);
        if(diaries != null){
            return ResponseEntity.ok(diaries);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 스크랩 기능
    @PostMapping("/{id}/scrap")
    public ResponseEntity<Diaries> scrapDiaries(@PathVariable String id) {
        Diaries scrapedDiaries = diariesService.scrapDiaries(id);
        if (scrapedDiaries != null) {
            return ResponseEntity.ok(scrapedDiaries);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 좋아요 순으로 정렬
    @GetMapping("/sorted/likes")
    public ResponseEntity<List<Diaries>> getDiariesSortedByLikes() {
        List<Diaries> sortedDiaries = diariesService.getDiariesSortedByLikes();
        return ResponseEntity.ok(sortedDiaries);
    }

    // 조회수 순으로 정렬
    @PostMapping("/{id}/comments")
    public ResponseEntity<Diaries> addComment(@PathVariable String id, @RequestBody DComment dcomment) {
        Diaries diaries = diariesService.addComment(id, dcomment);
        if (diaries != null) {
            return ResponseEntity.ok(diaries);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 댓글 작성
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<DComment>> getComments(@PathVariable String id) {
        List<DComment> comments = diariesService.getComments(id);
        if (comments != null) {
            return ResponseEntity.ok(comments);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}