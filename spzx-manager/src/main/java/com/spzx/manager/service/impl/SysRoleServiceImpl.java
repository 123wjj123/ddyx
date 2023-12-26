package com.spzx.manager.service.impl;

import com.spzx.manager.mapper.SysRoleMapper;
import com.spzx.manager.service.SysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import model.dto.system.SysRoleDto;
import model.entity.system.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    //角色列表的方法
    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
        //设置分页参数
        PageHelper.startPage(current,limit);
        //根据条件查询所有的数据
        List<SysRole> list= sysRoleMapper.findByPage(sysRoleDto);
        //封装pageInfo对象
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }
    //角色添加的方法
    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.save(sysRole);
    }
    //角色修改的方法
    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
    }
    //角色删除方法
    @Override
    public void deleteById(Long roleId) {
    sysRoleMapper.delete(roleId);

    }
}