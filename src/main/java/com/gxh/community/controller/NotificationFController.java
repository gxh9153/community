package com.gxh.community.controller;

import com.gxh.community.dto.NotificationDTO;
import com.gxh.community.dto.PaginationDTO;
import com.gxh.community.enums.NotificationEnum;
import com.gxh.community.mapper.NotificationMapper;
import com.gxh.community.model.User;
import com.gxh.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationFController {

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/notification/{id}")
    public String profile(@PathVariable("id") Long id,
                          HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id,user);

        if(NotificationEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationEnum.REPLY_QUESTION.getType() == notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterId();
        }else{
            return "redirect:/";
        }
    }
}
