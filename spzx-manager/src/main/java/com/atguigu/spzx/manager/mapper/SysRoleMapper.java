package com.atguigu.spzx.manager.mapper;

import model.dto.system.SysRoleDto;
import model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SysRoleMapper {
    //角色列表的方法
    List<SysRole> findByPage(SysRoleDto sysRoleDto);
    //角色添加的方法
    void save(SysRole sysRole);
    void update(SysRole sysRole);
    //角色删除方法
    void delete(Long roleId);
}