package com.gxh.community.controller;

import com.gxh.community.dto.QuestionDTO;
import com.gxh.community.dto.CommentDTO;
import com.gxh.community.enums.CommentTypeEnum;
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
    public String question(@PathVariable("id") Long id,Model model){
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        List<QuestionDTO> relationQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relationQuestions",relationQuestions);
        return "question";
    }
}
