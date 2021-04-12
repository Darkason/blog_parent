package com.darkason.img.service;

import com.darkason.common.response.Result;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @auth admin
 * @date
 * @Description
 */
public interface ImgService {

    Result uploadBlogPic(MultipartHttpServletRequest request);

}
