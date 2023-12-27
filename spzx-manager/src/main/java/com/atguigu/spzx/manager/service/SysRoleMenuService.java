package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;

import java.util.Map;
import java.util.Objects;

public interface SysRoleMenuService {

    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);


    //保存分配的数据
    void doAssign(AssginMenuDto assginMenuDto);
}
