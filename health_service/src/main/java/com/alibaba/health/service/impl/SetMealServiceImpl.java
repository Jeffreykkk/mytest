package com.alibaba.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.health.dao.SetmealDao;
import com.alibaba.health.entity.PageResult;
import com.alibaba.health.entity.QueryPageBean;
import com.alibaba.health.pojo.Setmeal;
import com.alibaba.health.service.SetMealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = SetMealService.class)
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetmealDao setmealDao;

    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //获取套餐
        setmealDao.add(setmeal);
        //拿到套餐的id
        Integer setmealId = setmeal.getId();
        if(null != checkgroupIds){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmealId,checkgroupId);
            }
            //事务控制
        }
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        //通过分页工具拿到分页查询需要的当前页面条件和大小
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //判断查询条件是否为空
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            //有查询条件，进行模糊查询
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        };
        //条件查询，这个查询语句会被分页
       Page<Setmeal> page =  setmealDao.findByCondition(queryPageBean.getQueryString());
       return new PageResult<Setmeal>(page.getTotal(),page.getResult());

    }
}
