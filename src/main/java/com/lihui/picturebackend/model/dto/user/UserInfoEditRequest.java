package com.lihui.picturebackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 用户更新请求
 */
public class UserInfoEditRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 简介
     */
    private String userProfile;



    private static final long serialVersionUID = 1L;
}