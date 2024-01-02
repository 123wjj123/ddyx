package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import model.vo.common.Result;
import model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/system/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;
    //菜单删除
    //查询用户可以操作菜单
    @GetMapping("/menus")
    public com.atguigu.spzx.model.vo.common.Result menus() {
        List<SysMenuVo> list = sysMenuService.findMenusByUserId();
        return com.atguigu.spzx.model.vo.common.Result.build(list, com.atguigu.spzx.model.vo.common.ResultCodeEnum.SUCCESS);
    }
    @DeleteMapping("removeById/{id}")
    public Result removeById(@PathVariable("id")Long id){
        sysMenuService.removeById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    //菜单修改
    @PutMapping("/update")
    public Result update(@RequestBody SysMenu sysMenu){
        sysMenuService.update(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);

    }
    //菜单添加
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);

    }
    //菜单列表
    @GetMapping("/findNodes")
    public Result findNodes(){
    List<SysMenu> list =sysMenuService.findNodes();
    return Result.build(list, ResultCodeEnum.SUCCESS);
    }

}
