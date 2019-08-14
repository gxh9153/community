package com.gxh.community.service;

import com.gxh.community.dto.PaginationDTO;
import com.gxh.community.dto.QuestionDTO;
import com.gxh.community.mapper.QuestionMapper;
import com.gxh.community.mapper.UserMapper;
import com.gxh.community.model.Question;
import com.gxh.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.count();
        if(totalCount % size ==0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size + 1;
        }
        //实现page<1的情况下符合逻辑
        if(page<1){
            page = 1;
        }
        if (page>totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        Integer offset = page < 1 ? 0 : size * (page - 1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question:questions) {
            User user = userMapper.findUserById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestionDTOS(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.countByUserId(userId);
        if(totalCount % size ==0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size + 1;
        }
        //实现page<1的情况下符合逻辑
        if(page<1){
            page = 1;
        }
        if (page>totalPage){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        Integer offset = page < 1 ? 0 : size * (page - 1);
        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question:questions) {
            User user = userMapper.findUserById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestionDTOS(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getQuestionById(Integer id) {
        Question question = questionMapper.getQuestionById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findUserById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
}
