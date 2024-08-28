package com.goorm.nyangnyam_back.service;

import com.goorm.nyangnyam_back.model.DiariesModel;
import com.goorm.nyangnyam_back.repository.DiariesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.List;

@Service
public class DiariesService {
    @Autowired
    private DiariesRepository diariesRepository;

    public List<DiariesModel> getAllDiaries() {
        return diariesRepository.findAll();
    }

    public DiariesModel getDiariesById(String id){
        return diariesRepository.findById(id).orElse(null);
    }

    public DiariesModel createDiaries(DiariesModel diariesModel){
        return diariesRepository.save(diariesModel);
    }

    public DiariesModel updateDiaries(String id, DiariesModel diariesModel){
        DiariesModel existingDiaries = diariesRepository.findById(id).orElse(null);
        if(existingDiaries != null) {
            existingDiaries.setImages(diariesModel.getImages());
            existingDiaries.setComments(diariesModel.getComments());
            existingDiaries.setPublicRange(diariesModel.getPublicRange());
            existingDiaries.setCategory(diariesModel.getCategory());
            existingDiaries.setGrade(diariesModel.getGrade());
            existingDiaries.setRecommend(diariesModel.getRecommend());
            existingDiaries.setUserId(diariesModel.getUserId());
        }
        return null;
    }

    public void deleteDiaries(String id){
        diariesRepository.deleteById(id);
    }

    public DiariesModel likeDiaries(String id){
        DiariesModel diariesModel = diariesRepository.findById(id).orElse(null);
        if(diariesModel != null) {
            diariesModel.setLikes(diariesModel.getLikes() + 1);
            return diariesRepository.save(diariesModel);
        }
        return null;
    }

    public DiariesModel scrapDiaries(String id){
        DiariesModel diariesModel = diariesRepository.findById(id).orElse(null);
        if(diariesModel != null) {
            diariesModel.setScraps(diariesModel.getScraps() + 1);
            return diariesRepository.save(diariesModel);
        }
        return null;
    }

    public List<DiariesModel> getDiariesSortedByLikes(){
        return diariesRepository.findAll(Sort.by(Sort.Direction.DESC, "likes"));
    }

    public DiariesModel addComment (String id, Comment comment){
        DiariesModel diariesModel = diariesRepository.findById(id).orElse(null);
        if(diariesModel != null){
            diariesModel.getCommentList().add(comment);
            return diariesRepository.save(diariesModel);
        }
        return null;
    }

    public List<Comment> getComments(String id){
        DiariesModel diariesModel = diariesRepository.findById(id).orElse(null);
        if(diariesModel != null){
            return diariesModel.getCommentList();
        }
        return null;
    }
}
