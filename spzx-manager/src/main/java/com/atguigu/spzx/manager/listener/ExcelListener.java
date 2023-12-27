package com.atguigu.spzx.manager.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import org.apache.poi.ss.formula.functions.T;

public class ExcelListener implements ReadListener<T> {

    // 通过构造来传递mapper,操作数据库

    // 从第二行开始读取，把每行读取内容封装到t对象里面
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {

    }

    // 所有操作执行完成才会执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
