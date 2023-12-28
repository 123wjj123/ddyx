package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {
    // 品牌列表 （分页）
    List<Brand> findByPage();

    // 品牌添加
    void save(Brand brand);

}
