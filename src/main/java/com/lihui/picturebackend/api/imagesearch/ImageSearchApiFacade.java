package com.lihui.picturebackend.api.imagesearch;

import com.lihui.picturebackend.api.imagesearch.model.ImageSearchResult;
import com.lihui.picturebackend.api.imagesearch.sub.GetImageFirstUrlApi;
import com.lihui.picturebackend.api.imagesearch.sub.GetImageListApi;
import com.lihui.picturebackend.api.imagesearch.sub.GetImagePageUrlApi;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ImageSearchApiFacade {

    /**
     * 搜索图片
     *
     * @param imageUrl
     * @return
     */
    public static List<ImageSearchResult> searchImage(String imageUrl) {
        String imagePageUrl = GetImagePageUrlApi.getImagePageUrl(imageUrl);
        String imageFirstUrl = GetImageFirstUrlApi.getImageFirstUrl(imagePageUrl);
        List<ImageSearchResult> imageList = GetImageListApi.getImageList(imageFirstUrl);
        return imageList;
    }

    public static void main(String[] args) {
        // 测试以图搜图功能
        String imageUrl = "https://lihui-1342295302.cos.ap-beijing.myqcloud.com/public/1890024982679416833/2025-02-24_Zxe3kh6UAY4ObTIw_thumbnail.png";
        List<ImageSearchResult> resultList = searchImage(imageUrl);
        System.out.println("结果列表" + resultList);
    }
}