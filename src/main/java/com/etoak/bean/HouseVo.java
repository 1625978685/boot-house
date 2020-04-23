package com.etoak.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class HouseVo extends House {

    //显示的中文名称
    private String rentModeName;
    //显示的中文名称
    private String houseTypeName;
    //显示的中文名称
    private String orientationName;

    //接受前端户型的参数
    @JsonIgnore //不把这个字段封装到返回结果 json中  ，也就是不返回
    private String[] houseTypeList;

    //接受前端朝向的参数
    @JsonIgnore
    private List<String> orientationList;

    @JsonIgnore   //不接受前端参数，为了封装价格参数
    private List<Map<String,Integer>> rentalMapList;

}
