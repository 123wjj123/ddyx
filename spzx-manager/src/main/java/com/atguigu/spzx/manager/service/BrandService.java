package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BrandService {

    // 品牌列表 （分页）
    PageInfo<Brand> findByPage(Integer page, Integer limit);

    // 品牌的添加
    void save(Brand brand);

    // 查询所有的品牌
    List<Brand> findAll();
}
