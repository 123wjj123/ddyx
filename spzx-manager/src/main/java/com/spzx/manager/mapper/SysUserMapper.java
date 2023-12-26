package com.spzx.manager.mapper;

import model.dto.system.SysUserDto;
import model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {

    //根据用户名查询数据库表
    List<SysUser> findByPage(SysUserDto sysUserDto);
    //用户添加
    void save(SysUser sysUser);
    //根据用户名查询数据库表sys_user表
    SysUser selectUserInfoByUserName(String userName);
    //用户修改
    void update(SysUser sysUser);


    void delete(Long userId);
}
