package com.etoak.mapper;


import com.etoak.bean.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AreaMapper {

    //根据pid查询子级列表
    List<Area> queryByPid(@Param("pid") int pid);


    Area queryById(@Param("id") int id);
}
