package com.atguigu.spzx.manager.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public class ExcelListener<T> implements ReadListener<T> {

    /*
        没隔5条存储数据库，实际使用中可以100条，然后清理List，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /*
        缓存的数据
     */
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    // 通过构造来传递mapper,操作数据库
    private CategoryMapper categoryMapper;
    public ExcelListener(CategoryMapper categoryMapper){
        this.categoryMapper = categoryMapper;
    }

    // 从第二行开始读取，把每行读取内容封装到t对象里面
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        // 把每行数据对象t放到cachedDataList集合里面
        cachedDataList.add(t);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易oom
        if (cachedDataList.size() >= BATCH_COUNT){
            // 调用方法一次性批量添加到数据库里面
            saveData();
            // 清理List集合
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }



    // 所有操作执行完成才会执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 如果数据达到100条数据就调上面的方法  如果没有达到就调该方法
        // 保存数据
        saveData();
    }

    // 保存的方法
    private void saveData() {
        categoryMapper.batchInsert((List<CategoryExcelVo>)cachedDataList);
    }
}
