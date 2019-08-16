package com.gxh.community.controller;

import com.gxh.community.dto.QuestionDTO;
import com.gxh.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @RequestMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id,
                           Model model){
        //累加阅读数
        questionService.incView(id);
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        model.addAttribute("questionDTO",questionDTO);
        return "question";
    }
}
