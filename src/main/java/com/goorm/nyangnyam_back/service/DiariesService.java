package com.goorm.nyangnyam_back.service;

import com.goorm.nyangnyam_back.model.Comment;
import com.goorm.nyangnyam_back.model.Diaries;
import com.goorm.nyangnyam_back.repository.DiariesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DiariesService {
    @Autowired
    private DiariesRepository diariesRepository;

    public List<Diaries> getAllDiaries() {
        return diariesRepository.findAll();
    }

    public Diaries getDiariesById(String id){
        return diariesRepository.findById(id).orElse(null);
    }

    public Diaries createDiaries(Diaries diaries){
        return diariesRepository.save(diaries);
    }

    public Diaries updateDiaries(String id, Diaries diaries){
        Diaries existingDiaries = diariesRepository.findById(id).orElse(null);
        if(existingDiaries != null) {
            existingDiaries.setImages(diaries.getImages());
            existingDiaries.setComments(diaries.getComments());
            existingDiaries.setPublicRange(diaries.getPublicRange());
            existingDiaries.setCategory(diaries.getCategory());
            existingDiaries.setGrade(diaries.getGrade());
            existingDiaries.setRecommend(diaries.getRecommend());
            existingDiaries.setUserId(diaries.getUserId());
        }
        return null;
    }

    public void deleteDiaries(String id){
        diariesRepository.deleteById(id);
    }

    public Diaries getDiariesSoretedByCategory(String category){
        return diariesRepository.findById(category).orElse(null);
    }

    public Diaries likeDiaries(String id){
        Diaries diaries = diariesRepository.findById(id).orElse(null);
        if(diaries != null) {
            diaries.setLikes(diaries.getLikes() + 1);
            return diariesRepository.save(diaries);
        }
        return null;
    }

    public Diaries scrapDiaries(String id){
        Diaries diaries = diariesRepository.findById(id).orElse(null);
        if(diaries != null) {
            diaries.setScraps(diaries.getScraps() + 1);
            return diariesRepository.save(diaries);
        }
        return null;
    }

    public List<Diaries> getDiariesSortedByLikes(){
        return diariesRepository.findAll(Sort.by(Sort.Direction.DESC, "likes"));
    }

//    public Diaries addComment (String id, Comment comment){
//        Diaries diaries = diariesRepository.findById(id).orElse(null);
//        if(diaries != null){
//            diaries.getCommentList().add(comment);
//            return diariesRepository.save(diaries);
//        }
//        return null;
//    }
//
//    public List<Comment> getComments(String id){
//        Diaries diaries = diariesRepository.findById(id).orElse(null);
//        if(diaries != null){
//            return diaries.getCommentList();
//        }
//        return null;
//    }
}
