package com.atguigu.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import model.dto.system.SysRoleDto;
import model.entity.system.SysRole;

public interface SysRoleService {
    //角色列表的方法
    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit);

    //角色添加的方法
    void saveSysRole(SysRole sysRole);
    //角色修改的方法
    void updateSysRole(SysRole sysRole);
    //角色删除方法
    void deleteById(Long roleId);
}
