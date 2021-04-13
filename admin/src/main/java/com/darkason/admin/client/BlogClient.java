package com.darkason.admin.client;

import com.darkason.admin.client.impl.BlogClientFallback;
import com.darkason.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "blog",fallback = BlogClientFallback.class)
public interface BlogClient {
    @RequestMapping(value = "blog/blog/deleteByUserId", method = RequestMethod.POST)
    Result deleteByUserId(@RequestParam("userId") Long userId);

    @RequestMapping(value = "blog/blog/getBlogNumByUserId", method = RequestMethod.POST)
    Result getBlogNumByUserId(@RequestParam Long userId);
}
