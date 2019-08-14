package com.gxh.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private Integer accountId;
    private String name;
    private String token;
    private long gmtCreate;
    private long gmtModified;
    private String bio;
    private String avatarUrl;

}
