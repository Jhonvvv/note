package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.ApiCode;
import com.example.demo.model.ReturnT;
import com.example.demo.model.User;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Api(value ="用户", tags ="用户接口")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @ApiOperation(value = "注册")
    @RequestMapping(value = "/registered",method = RequestMethod.POST)
    @Transactional
    public String registered(String jsonData){
        try{
            User user=new Gson().fromJson(jsonData,User.class);
            int insertFlag =userMapper.insert(user);
            if(insertFlag==1){
                return new Gson().toJson(ReturnT.success("注册成功"));
            }
        }catch (Exception e){
            Throwable cause = e.getCause();
            if(cause instanceof java.sql.SQLIntegrityConstraintViolationException){
                //错误信息
                String errMsg =    ((java.sql.SQLIntegrityConstraintViolationException)cause).getMessage();
                //根据约束名称定位是那个字段
                if(null!=errMsg && !"".equals(errMsg) && errMsg.indexOf("accountNumber") != -1) {
                    return new Gson().toJson(ReturnT.fail(ApiCode.API_repeat,"账号已存在"));
                }else{
                    return new Gson().toJson(ReturnT.fail(ApiCode.API_noRegion,"注册失败"));
                }
            }
        }
        return new Gson().toJson(ReturnT.fail(ApiCode.API_noRegion,"注册失败"));
    }

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/logIn",method = RequestMethod.POST)
    public String logIn(String jsonData){
        User user=new Gson().fromJson(jsonData,User.class);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("accountNumber",user.getAccountNumber());
        queryWrapper.eq("password",user.getPassword());
        User user1 =userMapper.selectOne(queryWrapper);
        if(null!=user1){
            return new Gson().toJson(ReturnT.success(user1));
        }else{
            return new Gson().toJson(ReturnT.fail(ApiCode.API_noRegion,"登录失败"));
        }

    }

}
