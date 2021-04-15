package com.darkason.img.service.impl;

import com.darkason.common.enums.StatusEnum;
import com.darkason.common.response.Result;
import com.darkason.img.client.BlogImageClient;
import com.darkason.img.entity.BlogImage;
import com.darkason.img.service.ImgService;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auth admin
 * @date
 * @Description
 */
@Service
public class ImgServiceImpl implements ImgService {

    private Logger logger = LoggerFactory.getLogger(ImgServiceImpl.class);

    @Resource
    private Environment environment;

    @Resource
    private BlogImageClient blogImageClient;

    @Override
    @GlobalTransactional(name = "seata_admin_blog", rollbackFor = Exception.class)
    public Result uploadBlogPic(MultipartHttpServletRequest request) {
        Result result = new Result(StatusEnum.SUCCESS);
        //博客图片
        List<BlogImage> blogImageList = new ArrayList<>();
        try {
            //文件前缀
            String prefix = environment.getProperty("img.prefix");

            List<MultipartFile> listFile = request.getFiles("imgPic");

            for (MultipartFile multipartFile : listFile) {
                //获取文件的原始名称(不包含完整路径)，比如：1.jpg,1.png
                String originalFilename = multipartFile.getOriginalFilename();
                String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

                if (!(fileType.equals("jpg") || fileType.equals("png") || fileType.equals("gif"))) {
                    //不是图片
                    result = new Result(StatusEnum.PARAM_EXCEPTION);
                    return result;
                }

                String newFileName = System.nanoTime() + "." + fileType;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                //文件后缀 2021-03-24 \\ 123456456468.jpg，可以加入模块名
                //每天生成一个文件夹，也可以每月
                String fileSuffix = sdf.format(new Date()) + "\\blog\\" + newFileName;

                //完整的文件路径
                String filePath = prefix + fileSuffix;

                File file = new File(filePath);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                FileCopyUtils.copy(multipartFile.getBytes(), file);

                //构造BlogImage实体类BlogImage
                BlogImage blogImage = new BlogImage();
                blogImage.setBlogId(null);
                blogImage.setName(originalFilename);
                blogImage.setFileUrl(fileSuffix);
                blogImage.setSize(multipartFile.getSize() + "");
                blogImage.setSuffix(fileType);
                //TODO 枚举
                blogImage.setIsActive(1);
                blogImage.setCreateTime(new Date());
                blogImageList.add(blogImage);
            }

            //把博客图片存入数据库
            //blogImageService.addBlogImageBatch(blogImageList);

            //改进：远程调用blog_image
            Result imgResult = blogImageClient.addBlogImageList(blogImageList);
            if (!imgResult.getCode().equals(StatusEnum.SUCCESS.getCode())) {
                throw new RuntimeException("添加博客图片错误");
            }

            //博客图片id
            // 1,2,3,
            result.setData(imgResult.getData());

        } catch (Exception e) {
            logger.error("上传博客图片错误", e);
            result = new Result(StatusEnum.FAIL);
        }

        return result;
    }
}
