package com.goorm.nyangnyam_back.service;

import com.goorm.nyangnyam_back.model.GroupOrder;
import com.goorm.nyangnyam_back.repository.GroupOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupOrderService {

    @Autowired
    private GroupOrderRepository groupOrderRepository;

    public void createOrder(GroupOrder groupOrder, MultipartFile imageFile) throws IOException {
        if (groupOrder.getContent() == null || groupOrder.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Content must not be null or empty");
        }

        if(imageFile != null && !imageFile.isEmpty()){
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/images";
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            imageFile.transferTo(saveFile);

            groupOrder.setImagePath("/images/" + fileName);
        }

        groupOrderRepository.save(groupOrder);
    }
    public Optional<GroupOrder> findGroupOrderById(Integer id) {
        return groupOrderRepository.findById(id);
    }

    public List<GroupOrder> getAllOrders() {
        return groupOrderRepository.findAll();
    }

    public void joinOrder(int orderId, String participant){
        Optional<GroupOrder> optionalOrder = groupOrderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            GroupOrder groupOrder = optionalOrder.get();
            groupOrder.getParticipants().add(participant);
            groupOrder.setTeamSize(groupOrder.getTeamSize()+1);
            groupOrderRepository.save(groupOrder);
        }
    }
}
