package com.goorm.nyangnyam_back.controller;

import com.goorm.nyangnyam_back.model.DiariesModel;
import com.goorm.nyangnyam_back.service.DiariesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;
import java.util.List;

@RestController
@RequestMapping("/diaries")
public class DiariesController {

    @Autowired
    private DiariesService diariesService;

    @GetMapping
    public List<DiariesModel> getAllDiaries(){
        return diariesService.getAllDiaries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiariesModel> getDiariesById(@PathVariable String id){
        DiariesModel diariesModel = diariesService.getDiariesById(id);
        if(diariesModel != null){
            return ResponseEntity.ok(diariesModel);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public DiariesModel createDiaries(@RequestBody DiariesModel diariesModel) {
        return diariesService.createDiaries(diariesModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiariesModel> updateDiaries(@PathVariable String id, @RequestBody DiariesModel diariesModel) {
        DiariesModel updatedDiaries = diariesService.updateDiaries(id, diariesModel);
        if (updatedDiaries != null) {
            return ResponseEntity.ok(updatedDiaries);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiaries(@PathVariable String id) {
        diariesService.deleteDiaries(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<DiariesModel> likeDiaries(@PathVariable String id) {
        DiariesModel likedDiaries = diariesService.likeDiaries(id);
        if (likedDiaries != null) {
            return ResponseEntity.ok(likedDiaries);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{id}/scrap")
    public ResponseEntity<DiariesModel> scrapDiaries(@PathVariable String id) {
        DiariesModel scrapedDiaries = diariesService.scrapDiaries(id);
        if (scrapedDiaries != null) {
            return ResponseEntity.ok(scrapedDiaries);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/sorted/likes")
    public ResponseEntity<List<DiariesModel>> getDiariesSortedByLikes() {
        List<DiariesModel> sortedDiaries = diariesService.getDiariesSortedByLikes();
        return ResponseEntity.ok(sortedDiaries);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<DiariesModel> addComment(@PathVariable String id, @RequestBody Comment comment) {
        DiariesModel diaries = diariesService.addComment(id, comment);
        if (diaries != null) {
            return ResponseEntity.ok(diaries);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable String id) {
        List<Comment> comments = diariesService.getComments(id);
        if (comments != null) {
            return ResponseEntity.ok(comments);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}