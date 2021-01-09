package com.alibaba.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.health.constant.MessageConstant;
import com.alibaba.health.entity.PageResult;
import com.alibaba.health.entity.QueryPageBean;
import com.alibaba.health.entity.Result;
import com.alibaba.health.pojo.CheckItem;
import com.alibaba.health.service.CheckItemService;

import javafx.scene.control.Pagination;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    /**
     * 订阅检查项服务
     */
    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询检查项
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(){
       List<CheckItem> list =  checkItemService.findAll();
       //封装成result返回
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }

    /**
     * 添加检查项
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        //调用服务添加
        checkItemService.add(checkItem);
        //放回响应结果
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckItem> pageResult =  checkItemService.findPage(queryPageBean);

        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
    }

    @GetMapping("/findById")
    public Result findById(int id){
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    /**
     * 修改检查项
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        //调用服务添加
        checkItemService.update(checkItem);
        //放回响应结果
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }



}
