package com.gxh.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    //该变量是静态的
    QUESTION_NOT_FOUND(2001,"哎呀，您访问的资源已经不在了，要不您换一个试试"),
    TARGET_PARAM_NOT_FOUND(2002,"未选择任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后重试"),
    SYSTEM_ERROR(2004,"服务器太忙啦，你要不稍后重试下试试!!!!"),
    TYPE_PARAM_WRONG(2005,"评论类型错误，或不存在"),
    COMMENT_NOT_FOUND(2006,"此评论没有找到"),
    COMMENT_IS_EMPTY(2007,"评论不能为空"),
    READ_NOTIFICATION_FAIL(2008,"大哥，你怎么可以读别人的信息呢"),
    NOTIFICATION_NOT_FOUND(2009,"哎呀，您访问的通知已经不存在了呢");
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
