package com.atguigu.spzx.manager.service.impl;


import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.mapper.SysRoleUserMapper;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.manager.utils.MenuHelpeer;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import common.exception.GuiguException;
import model.dto.system.SysUserDto;
import model.entity.system.SysUser;
import model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import utils.AuthContextUtil;

import java.util.List;
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
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
    //用户分配角色
    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
         //根据userId删除线用户之前分配角色数据
        sysRoleUserMapper.deleteByUserId(assginRoleDto.getUserId());
        //重新分配新的数据
        List<Long>roleIdList=assginRoleDto.getRoleIdList();
        //遍历得到每个角色id
        for (Long roleId:roleIdList) {
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId(),roleId);
        }
    }
    //查询用户可以操作菜单
    @Override
    public List<SysMenuVo> findMenusByUserId() {
        //获取当前用户id
        SysUser sysUser= AuthContextUtil.get();
        Long userId= sysUser.getId();

        //根据userId查询可以操作菜单
        List<SysMenu> syMenuList = sysMenuMapper.findMenusByUserId(userId);

        //封装成要求的数据格式 返回
        List<SysMenu> sysMenuList = MenuHelpeer.buildTree(syMenuList);
        return null;
    }
}
