package com.alibaba.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.health.entity.PageResult;
import com.alibaba.health.entity.QueryPageBean;
import com.alibaba.health.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;

public interface SetMealService {
    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);
}
