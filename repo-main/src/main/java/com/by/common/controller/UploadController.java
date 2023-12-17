package com.by.common.controller;

import com.by.common.bean.ResultBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
public class UploadController extends BaseController {

    private final String DIR = "attachment";

    //文件上传的 类型
    private Set<String> fileTypes = new HashSet<>();

    {
        fileTypes.add("image/jpeg");
        fileTypes.add("image/png");
    }

    /**
     * 文件上传
     *
     * @param file    上传文件的参数名
     * @param request servlet request接口 获取服务器地址
     * @return
     */
    @PostMapping("/upload")
    public ResultBean upload(MultipartFile file, HttpServletRequest request) {
        //判断文件上传的类型
        if (!fileTypes.contains(file.getContentType())) {
            return fail("上传失败", "请上传jpeg/png格式的文件");
        }

        File dir = initStoreDir(request);
        //新的文件名
        String newFileName = getNewFileName(file);
        try {
            //上传文件  文件流的读写
            file.transferTo(new File(dir, newFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return success("上传成功", DIR + "/" + newFileName);
    }


    @PostMapping("batchupload")
    public ResultBean batchUpload(List<MultipartFile> files, HttpServletRequest request) {
        File dir = initStoreDir(request);
        //上传后服务器上的存储目录
        List result = new ArrayList();
        //遍历上传文件
        files.forEach(file -> {
            //执行上传
            //新的文件名
            String newFileName = getNewFileName(file);
            //上传文件
            try {
                file.transferTo(new File(dir, newFileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //记录上传后的目录
            result.add(DIR + "/" + newFileName);
        });
        return success("上传成功", result);
    }

    /**
     * 初始附件存储目录
     *
     * @param request
     * @return 附件在服务器上存储的目录
     */
    File initStoreDir(HttpServletRequest request) {
        //获取服务器根目录
        String root = request.getServletContext().getRealPath("/");
        //判断保存附件的目录是否存在
        File dir = new File(root + DIR);
        if (!dir.exists()) {
            //创建目录
            dir.mkdirs();
        }
        return dir;
    }


    /**
     * 获取新的文件名
     *
     * @param file
     * @return
     */
    String getNewFileName(MultipartFile file) {
        //获取上传文件名称
        String fileName = file.getOriginalFilename();
        //获取文件后缀
        String ext = fileName.substring(fileName.lastIndexOf("."));
        //新的文件名
        String newFileName = UUID.randomUUID().toString() + ext;
        return newFileName;
    }

}
