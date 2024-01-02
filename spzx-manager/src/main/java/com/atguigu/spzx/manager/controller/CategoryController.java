package com.atguigu.spzx.manager.controller;


import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import model.vo.common.Result;
import model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    // 导入
    @PostMapping("/importData")
    public Result importData(MultipartFile file){
        // 获取上传文件
        categoryService.importData(file);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }


    // 导出 (本质是文件下载)
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response){
        categoryService.exportData(response);
    }


    // 分类列表，每次查询一层数据
    // select * from category where parent_id=id
    @GetMapping("/findCategoryList/{id}")
    public Result findCategoryList(@PathVariable Long id){
        List<Category> list = categoryService.findCategoryList(id);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

}
