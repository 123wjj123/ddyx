package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.FileUploadService;
import model.vo.common.Result;
import model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/system")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/fileUpload")
    public Result fileUpload(@RequestParam("file")MultipartFile file){
        //1.获取上传文件
        String url=fileUploadService.upload(file);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
