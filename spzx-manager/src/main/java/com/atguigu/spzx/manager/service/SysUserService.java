package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.github.pagehelper.PageInfo;
import model.dto.system.SysUserDto;
import model.entity.system.SysUser;

import java.util.List;

//用户条件分页查询接口
public interface SysUserService {
    PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);
    //用户删除
    void deleteById(Long userId);
    //用户分配角色
    void doAssign(AssginRoleDto assginRoleDto);
    //查询用户可以操作菜单
    List<SysMenuVo> findMenusByUserId();
}
