package com.gxh.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer parentId;
    private String context;
    private Integer type;
}
