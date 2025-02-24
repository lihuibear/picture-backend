package com.lihui.picturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lihui.picturebackend.model.dto.space.SpaceAddRequest;
import com.lihui.picturebackend.model.dto.space.SpaceQueryRequest;
import com.lihui.picturebackend.model.entity.Space;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lihui.picturebackend.model.entity.User;
import com.lihui.picturebackend.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lihui
 * @description 针对表【space(空间)】的数据库操作Service
 * @createDate 2025-02-22 14:28:44
 */
public interface SpaceService extends IService<Space> {

    /**
     * 添加空间
     *
     * @param spaceAddRequest
     * @param loginUser
     * @return
     */
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    /**
     * 验证空间信息
     *
     * @param space
     * @param add
     */
    void validSpace(Space space, boolean add);

    /**
     * 根据空间信息获取空间VO
     *
     * @param space
     * @param request
     * @return
     */
    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    /**
     * 获取空间分页信息
     *
     * @param spacePage
     * @param request
     * @return
     */
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    /**
     * 根据查询条件获取查询条件构造器
     *
     * @param spaceQueryRequest
     * @return
     */
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    /**
     * 根据空间级别，自动填充限额
     *
     * @param space
     */
    void fillSpaceBySpaceLevel(Space space);
}
