package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysRoleService;
import com.github.pagehelper.PageInfo;
import model.dto.system.SysRoleDto;
import model.entity.system.SysRole;
import model.vo.common.Result;
import model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @GetMapping("/findAllRoles")
    public Result findAllRoles(){
        Map<String,Object> map=sysRoleService.findAll();
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    //角色删除方法
    @DeleteMapping("/deleteById/{roleId}")
    public Result deleteById(@PathVariable("roleId")Long roleId){
        sysRoleService.deleteById(roleId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    //角色修改的方法
    @PutMapping("/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole) {
            sysRoleService.updateSysRole(sysRole);
            return Result.build(null,ResultCodeEnum.SUCCESS);

    }

    //角色添加的方法
    @PostMapping(value = "/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole) {
            sysRoleService.saveSysRole(sysRole);
            return Result.build(null, ResultCodeEnum.SUCCESS);

    }
    //角色列表的方法
    //current:当前页 limit:每页显示记录数
    //SysRoleDto:条件角色名称对象
    @PostMapping("/findByPage/{current}/{limit}")
    public Result findByPage(@PathVariable("current")Integer current,
                             @PathVariable("limit")Integer limit,
                             @RequestBody SysRoleDto sysRoleDto
                             ){
            //pageHelper插件实现分页
        PageInfo <SysRole> pageInfo=sysRoleService.findByPage(sysRoleDto,current,limit);
        return Result.build(pageInfo,ResultCodeEnum.SUCCESS);


    }


    }


