package com.goorm.nyangnyam_back.controller;

import com.goorm.nyangnyam_back.model.GroupOrder;
import com.goorm.nyangnyam_back.service.GroupOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class GroupOrderController {

    @Autowired
    private GroupOrderService groupOrderService;

    @GetMapping("/group-order/write")
    public String showOrderForm(Model model) {
        model.addAttribute("groupOrder", new GroupOrder());
        return "group-order/grouporderform";  // Correct path to your form template
    }

    @PostMapping("/group-order/write")
    public String createOrder(GroupOrder groupOrder, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            if (groupOrder.getContent() == null || groupOrder.getContent().trim().isEmpty()) {
                return "redirect:/group-order/write?error=content";
            }
            if (groupOrder.getTeamSize() <= 0) {
                return "redirect:/group-order/write?error=teamSize";
            }
            if (groupOrder.getDeliveryLocation() == null || groupOrder.getDeliveryLocation().trim().isEmpty()) {
                return "redirect:/group-order/write?error=deliveryLocation";
            }
            if (groupOrder.getMenu() == null || groupOrder.getMenu().trim().isEmpty()) {
                return "redirect:/group-order/write?error=menu";

            }
            groupOrderService.createOrder(groupOrder, imageFile);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/group-order/write?error";
        }
        return "redirect:/group-order/list";
    }

    @GetMapping("/group-order/list")
    public String listOrders(Model model) {
        model.addAttribute("orders", groupOrderService.getAllOrders());
        return "group-order/grouporderlist";  // Correct path to your list template
    }

    @PostMapping("/group-order/join")
    public String joinOrder(@RequestParam("orderId") int orderId, @RequestParam("participant") String participant) {
        try {
            groupOrderService.joinOrder(orderId, participant);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/group-order/list?error=exception";
        }
        return "redirect:/group-order/list";
    }
}
