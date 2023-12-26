package com.spzx.manager.service.impl;


import com.spzx.manager.mapper.SysUserMapper;
import com.spzx.manager.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import common.exception.GuiguException;
import model.dto.system.SysUserDto;
import model.entity.system.SysUser;
import model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import java.util.List;

public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public PageInfo<SysUser> findByPage(Integer pageNum,
                                        Integer pageSize, SysUserDto sysUserDto) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser>list=sysUserMapper.findByPage(sysUserDto);
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }
    //用户添加
    @Override
    public void saveSysUser(SysUser sysUser) {
        //判断用户名不能重复
        String userName = sysUser.getUserName();
        SysUser dbSysUser=sysUserMapper.selectUserInfoByUserName(userName);
        if (dbSysUser!=null){
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

          //密码输入进行加密
        String md5_password= DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        sysUser.setPassword(md5_password);
        sysUser.setStatus(1);
        //输入密码进行加密
        sysUserMapper.save(sysUser);
    }
    //用户修改
    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }
    //用户删除
    @Override
    public void deleteById(Long userId) {
        sysUserMapper.delete(userId);
    }
}
