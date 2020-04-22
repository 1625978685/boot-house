package com.etoak.bean;

import io.swagger.models.auth.In;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Data
public class House {

    private Integer id;

    @NotNull(message = "省必填")
    private Integer province;

    @NotNull(message = "市必填")
    private Integer city;

    @NotNull(message = "区县必填")
    private Integer area;

    //所在区县名称   可以前端传入，也可在后端根据area查询
    private String areaName;

    //租赁方式
    @NotBlank(message = "租赁方式不能为空")
    private String rentMode;

    //朝向
    private String orientation;

    //户型
    private String houseType;

    //租金
    @NotNull(message = "租金必填")
    @Min(value = 100, message = "租金不能少于100")
    @Max(value = 100000, message = "租金不能超过10万")
    private Integer rental;

    //地址
    @Size(min = 1, max = 10, message = "地址长度介于1-10个字符之间")
    @NotNull(message = "地址必填")
    private String address;

    //房屋图片地址
    private String pic;

    //发布时间
    private String publishTime;
}
