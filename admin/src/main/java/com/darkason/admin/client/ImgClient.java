package com.darkason.admin.client;

import com.darkason.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@FeignClient(value = "img")
public interface ImgClient {

    @RequestMapping(value = "/img/img/uploadBlogPic", method = RequestMethod.POST)
    Result uploadBlogPic(@RequestParam MultipartHttpServletRequest request);

}
