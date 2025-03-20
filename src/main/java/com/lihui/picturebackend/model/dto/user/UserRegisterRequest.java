package com.lihui.picturebackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 */

@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 929320015853414484L;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 确认密码
     */
    private String checkPassword;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 验证码
     *
     */
    private String verifyCode;
}
