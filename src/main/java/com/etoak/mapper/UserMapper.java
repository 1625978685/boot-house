package com.etoak.mapper;

import com.etoak.bean.User;
import org.apache.ibatis.annotations.Param;


public interface UserMapper {

    //注册用户
    int addUser(User user);


    /**
     * 根据用户名查询用户
     * 检验用户名唯一性，不重名
     * @param name
     * @return
     */
    User queryByName(@Param("name") String name);



}
