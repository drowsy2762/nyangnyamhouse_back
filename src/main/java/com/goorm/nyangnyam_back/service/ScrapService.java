package com.goorm.nyangnyam_back.service;

import com.goorm.nyangnyam_back.document.User;
import com.goorm.nyangnyam_back.model.Scrap;
import com.goorm.nyangnyam_back.repository.ScrapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapService {

    @Autowired
    private ScrapRepository scrapRepository;

    // 스크랩 저장
    public void saveScrap(Scrap scrap) {
        scrapRepository.save(scrap);
    }

    // 사용자별 스크랩 조회
    public List<Scrap> getScrapsByUser(User user) {
        return scrapRepository.findByUser(user);
    }

    // 스크랩 삭제
    public void deleteScrap(Integer id) {
        scrapRepository.deleteById(id);
    }
}
