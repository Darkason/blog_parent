package com.darkason.img.controller;

import com.darkason.common.response.Result;
import com.darkason.img.service.ImgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @auth admin
 * @date
 * @Description
 */
@RestController
@RequestMapping("/img/img")
public class ImgController {

    private Logger logger = LoggerFactory.getLogger(ImgController.class);

    @Autowired
    private ImgService imgService;

    /*
     * @description 上传博客图片
     */
    @RequestMapping(value = "uploadBlogPic", method = RequestMethod.POST)
    public Result uploadBlogPic(MultipartHttpServletRequest request) {
        return imgService.uploadBlogPic(request);
    }
}
