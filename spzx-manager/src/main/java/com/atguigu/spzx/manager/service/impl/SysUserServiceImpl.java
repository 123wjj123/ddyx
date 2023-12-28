package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.util.StringUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RedisTemplate<String,String > redisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {

        //检验图片验证码是否正确
        String codeKey = loginDto.getCodeKey();
        String captcha = loginDto.getCaptcha();
        //从redis中或验证码
        String s = redisTemplate.opsForValue().get("user:login:validatecode" + codeKey);
        if (StringUtil.isEmpty(s) || !StrUtil.equalsAnyIgnoreCase(captcha,s)){
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //验证通过删除redis中的验证码
        redisTemplate.delete("user:login:validatecode" + codeKey);
        //获得名字
        String userName = loginDto.getUserName();
        //通过名字获取获取数据库 sys_user表
        SysUser sysUser =sysUserMapper.selectUserInfoByUserName(userName);
        //判断是否为空
        if (sysUser==null){
            //throw new RuntimeException("用户名不存在");
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        //通过名字获得密码
        String dataBase_password = sysUser.getPassword();
        String input_password = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        //名字加密然后看是否相等
        if (!input_password.equals(dataBase_password)){
            //throw new RuntimeException("密码错误");
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        //获得特定的令牌 唯一标识 token
        String token = UUID.randomUUID().toString().replaceAll("-","");
        //把登录成功信息放进redis里面
        //key token //value: 用户信息
        redisTemplate.opsForValue().set("user:login"+token, JSON.toJSONString(sysUser),7, TimeUnit.DAYS);
        //返回LoginVo对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;

    }

    //获取用户信息
    @Override
    public SysUser getUserInfo(String token) {

        String userJson = redisTemplate.opsForValue().get("user:login"+token);

        return JSON.parseObject(userJson,SysUser.class);
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login"+token);
    }
}
