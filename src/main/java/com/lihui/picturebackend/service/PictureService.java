package com.lihui.picturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lihui.picturebackend.model.dto.picture.PictureQueryRequest;
import com.lihui.picturebackend.model.dto.picture.PictureUploadRequest;
import com.lihui.picturebackend.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lihui.picturebackend.model.entity.User;
import com.lihui.picturebackend.model.vo.PictureVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lihui
 * @description 针对表【picture(图片)】的数据库操作Service
 * @createDate 2025-02-15 21:17:50
 */
public interface PictureService extends IService<Picture> {
    /**
     * 上传图片
     *
     * @param multipartFile
     * @param pictureUploadRequest
     * @param loginUser
     * @return
     */
    PictureVO uploadPicture(MultipartFile multipartFile,
                            PictureUploadRequest pictureUploadRequest,
                            User loginUser);

    /**
     * 获取查询条件
     *
     * @param pictureQueryRequest
     * @return
     */
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    /**
     * 获取图片VO
     *
     * @param picture
     * @param request
     * @return
     */
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    /**
     * 获取图片VO分页
     *
     * @param picturePage
     * @param request
     * @return
     */
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    /**
     * 图片数据校验方法，用于更新和修改图片时进行判断
     *
     * @param picture
     */
    void validPicture(Picture picture);
}
