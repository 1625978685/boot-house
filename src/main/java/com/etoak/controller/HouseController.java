package com.etoak.controller;

import com.etoak.bean.House;
import com.etoak.bean.HouseVo;
import com.etoak.bean.Page;
import com.etoak.exception.ParamException;
import com.etoak.service.HouseService;
import com.etoak.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.Validation;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/house")
@Slf4j
public class HouseController {

    //读取配置文件，获取文件上传目录
    @Value("${upload.dir}")
    private String uploadDirectory;

    //读取配置文件，获取图片访问路径前缀
    @Value("${upload.savePathPrefix}")
    private String savePathPrefix;

    @Autowired
    HouseService houseService;


    //跳转到添加页面
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "house/add";
    }


    //添加房源信息
    @PostMapping("/add")
    public String add(@RequestParam("file")MultipartFile file,
                      @Valid House house,
                      BindingResult bindingResult) throws IOException, IllegalStateException {

        //校验参数
        ValidationUtil.validate(house);

        //上传文件
        //原始文件名
        String originalFilename = file.getOriginalFilename();

        //拿到文件后缀
        //String suffix = FilenameUtils.getExtension(originalFilename);

        //创建新的文件名称
        String prefix = UUID.randomUUID().toString().replaceAll("-","");
        String newFileName =  prefix + "_" + originalFilename;

        File destFile = new File(this.uploadDirectory,newFileName);
        file.transferTo(destFile);

        //给house设置访问地址
        house.setPic(this.savePathPrefix + newFileName);

        //添加房源
        houseService.addHouse(house);

        return "redirect:/house/toAdd";
    }



    //添加房源信息    第二种方法   在这暂时无用
    @PostMapping("/add2")
    public String add2(@RequestParam("file") MultipartFile file,
                      @Valid House house,
                      BindingResult bindingResult) throws IOException, IllegalStateException {

        //对参数进行后端校验
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if(CollectionUtils.isNotEmpty(allErrors)){
            StringBuffer msgBuffer = new StringBuffer();
            for(ObjectError objectError : allErrors){
                String message = objectError.getDefaultMessage();
                msgBuffer.append(message).append(",");
            }
            throw new ParamException("参数校验失败: " + msgBuffer.toString());
        }


        //上传文件
        //原始文件名
        String originalFilename = file.getOriginalFilename();

        //拿到文件后缀
        //String suffix = FilenameUtils.getExtension(originalFilename);

        //创建新的文件名称
        String prefix = UUID.randomUUID().toString().replaceAll("-","");
        String newFileName =  prefix + "_" + originalFilename;

        File destFile = new File(this.uploadDirectory,newFileName);
        file.transferTo(destFile);

        //给house设置访问地址
        house.setPic(this.savePathPrefix + newFileName);

        //添加房源
        houseService.addHouse(house);

        return "redirect:/house/toAdd";
    }


    /**
     * 房源列表查询
     * @param pageNum   页码
     * @param pageSize  每页记录数
     * @param houseVo   查询条件
     * @return
     */
    @GetMapping(value = "/list", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Page<HouseVo> queryList(@RequestParam(required = false,defaultValue = "1") int pageNum,
                                   @RequestParam(required = false,defaultValue = "10") int pageSize,
                                   HouseVo houseVo,
                                   @RequestParam(value = "rentalList[]",required = false) String[] rentalList){

        log.info("pageNum:{}, pageSize:{},houseVo:{}",pageNum,pageSize,houseVo);
        return houseService.queryList(pageNum,pageSize,houseVo,rentalList);

    }


    /**
     * 跳转到列表页面
     * @return
     */
    @GetMapping("/toList")
    public String toList(){
        return "house/list";
    }


    @PutMapping("/update")
    public String  update(House house){
        log.info("house: {}", house);
        houseService.updateHouse(house);
        return "redirect:/house/toList";
    }


    @DeleteMapping("/{id}")
    public String deleteHouse(@PathVariable("id") int id){
        log.info("delete id: {}",id);
        houseService.deleteById(id);
        return "redirect:/house/toList";
    }








}
