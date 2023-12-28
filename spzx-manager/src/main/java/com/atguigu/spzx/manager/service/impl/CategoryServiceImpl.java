package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.manager.listener.ExcelListener;
import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import common.exception.GuiguException;
import jakarta.servlet.http.HttpServletResponse;
import model.vo.common.ResultCodeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> findCategoryList(Long id) {

        //1 根据id条件值进行查询，返回list集合
        // select * from category where parent_id=id
        List<Category> categoryList = categoryMapper.selectCategoryByParentId(id);

        //2 遍历返回list集合
        // 判断每个分类是否有下一层分类，如果有设置 hasChildren = true
        if (!CollectionUtils.isEmpty(categoryList)){
            categoryList.forEach(category -> {
                // 判断每个分类是否有下一层分类
                // select count(*) from category where parent_id=1
                int count = categoryMapper.selectCountByParentId(category.getId());
                if (count > 0){ // 下一层分类
                    category.setHasChildren(true);
                } else {
                    category.setHasChildren(false);
                }
            });
        }

        return categoryList;
    }




    // 导出
    @Override
    public void exportData(HttpServletResponse response) {
        try{
            //1 设置响应头信息和其他信息 (设置表格类型)
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // 这里URLEncoder.encode可以防止中文乱码  当然和easyexcel没有关系
            String fileName = URLEncoder.encode("分类数据","UTF-8");

            // 设置响应头信息   Content-disposition:表示文件以下载方式打开
            response.setHeader("Content-disposition","attachment;filename="+fileName+".xlsx");

            //2 调用mapper方法查询所有分类，返回list集合
            List<Category> categoryList = categoryMapper.findAll();

            // 最终数据的list集合
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>();
            // List<Category>  转换为  List<CategoryExcelVo>  如果不转换 类型不一致  所以数据加不到数据表格中去
            for (Category category: categoryList) {
                    CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                    // 把 category值获取出来，设置到categoryExcelVo里面
                // 这样做太麻烦 如果有很多值的话就得一个一个设置
                /*Long id = category.getId();
                categoryExcelVo.setId(id);*/
                // spring中有个工具类 可以一下都设置进去  category中的值复制到categoryExcelVo  他会根据字段名来进行复制
                BeanUtils.copyProperties(category,categoryExcelVo);
                categoryExcelVoList.add(categoryExcelVo);
            }

            //3 调用EasyExcel的write方法完成写操作
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet("分类数据").doWrite(categoryExcelVoList);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
    }



    // 导入
    @Override
    public void importData(MultipartFile file) {
        //监听器
        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener(categoryMapper);
        try {
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class,null)
                    .sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
