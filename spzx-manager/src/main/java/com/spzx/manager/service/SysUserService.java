package com.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import model.dto.system.SysUserDto;
import model.entity.system.SysUser;
//用户条件分页查询接口
public interface SysUserService {
    PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);
    //用户删除
    void deleteById(Long userId);
}
