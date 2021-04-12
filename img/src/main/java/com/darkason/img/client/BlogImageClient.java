package com.darkason.img.client;

import com.darkason.common.response.Result;
import com.darkason.img.entity.BlogImage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @auth admin
 * @date
 * @Description
 */
@FeignClient("blog")
public interface BlogImageClient {

    @RequestMapping(value = "blog/image/addBlogImageList", method = RequestMethod.POST)
    Result addBlogImageList(@RequestBody @Validated List<BlogImage> list);
}
