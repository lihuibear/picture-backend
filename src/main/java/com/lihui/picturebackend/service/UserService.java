package com.lihui.picturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lihui.picturebackend.model.dto.user.UserQueryRequest;
import com.lihui.picturebackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lihui.picturebackend.model.vo.LoginUserVO;
import com.lihui.picturebackend.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lihui
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2025-02-13 20:15:10
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
//    long userRegister(String userAccount, String userPassword, String checkPassword);

    long userRegister(String userAccount, String userPassword, String checkPassword, String email, String verifyCode);

    /**
     * 用户密码加密
     *
     * @param userPassword
     * @return
     */
    String getEncryptPassword(String userPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取当前登录用户信息(脱敏)
     *
     * @param user
     * @return
     */

    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取用户信息(脱敏)
     *
     * @param user
     * @return
     */
    UserVO getUserVO(User user);

    /**
     * 获取用户信息列表(脱敏)
     *
     * @param userList
     * @return
     */
    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取用户列表
     *
     * @param userQueryRequest
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);

    boolean isSUAdmin(User user);
}
