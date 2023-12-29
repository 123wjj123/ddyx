package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageInfo;
import model.vo.common.Result;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface ProductSpecService {

    // 列表
    PageInfo<ProductSpec> findByPage(Integer page, Integer limit);


    // 添加
    void save(ProductSpec productSpec);

    // 修改
    void updateById(ProductSpec productSpec);

    // 删除
    void deleteById(Long id);

    // 查询所有商品规格
    List<ProductSpec> findAll();
}
