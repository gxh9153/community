package com.gxh.community.controller;

import com.gxh.community.dto.QuestionDTO;
import com.gxh.community.dto.CommentDTO;
import com.gxh.community.service.CommentService;
import com.gxh.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @RequestMapping("/question/{id}")
    public String question(@PathVariable("id") Long id,
                           Model model){
        //累加阅读数
        questionService.incView(id);
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        List<CommentDTO> comments = commentService.listByQuestionId(id);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("comments",comments);
        return "question";
    }
}
