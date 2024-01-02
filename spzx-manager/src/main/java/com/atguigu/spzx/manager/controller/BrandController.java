package com.atguigu.spzx.manager.controller;


import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;


    // 查询所有的品牌
    @GetMapping("/findAll")
    public Result findAll(){
        List<Brand> list = brandService.findAll();
        return Result.build(list,ResultCodeEnum.SUCCESS);
    }


    // 品牌列表 （分页）
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Integer page,@PathVariable Integer limit){
        PageInfo<Brand> pageInfo = brandService.findByPage(page,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }


    // 品牌的 添加
    @PostMapping("/save")
    public Result save(@RequestBody Brand brand){

        brandService.save(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
