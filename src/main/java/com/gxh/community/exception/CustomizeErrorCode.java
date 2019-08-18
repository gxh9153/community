package com.gxh.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    //该变量是静态的
    QUESTION_NOT_FOUND(2001,"哎呀，您访问的资源已经不在了，要不您换一个试试"),
    TARGET_PARAM_NOT_FOUND(2002,"未选择任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后重试"),
    SYSTEM_ERROR(2004,"服务器太忙啦，你要不稍后重试下试试!!!!"),
    TYPE_PARAM_WRONG(2005,"评论类型错误，或不存在"),
    COMMENT_NOT_FOUND(2006,"此评论没有找到");
    private String message;
    private Integer code;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
