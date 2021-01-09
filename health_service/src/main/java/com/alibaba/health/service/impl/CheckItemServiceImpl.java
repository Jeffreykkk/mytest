package com.alibaba.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.health.dao.CheckItemDao;
import com.alibaba.health.entity.PageResult;
import com.alibaba.health.entity.QueryPageBean;
import com.alibaba.health.pojo.CheckItem;
import com.alibaba.health.service.CheckItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * 检查项服务
 */
//使用alibaba的包，发布服务 interfaceClass指定的类
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    /**
     *同个工程下注入CheckItemDao,调用checkItemDao方法
     */
    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 查询所有检查项
     * @return
     */
    @Override
    public List<CheckItem> findAll(){

        return checkItemDao.findAll();
    }

    /**
     * 新增检查项
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem){
        checkItemDao.add(checkItem);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //模糊查询 拼接%
        //判断是否有查询条件
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            //有查询条件，进行模糊查询
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        Page<CheckItem> page  = checkItemDao.findByCondition(queryPageBean.getQueryString());
        PageResult<CheckItem> pageResult = new PageResult<CheckItem>(page.getTotal(),page.getResult());
        return pageResult;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(int id) {
       return checkItemDao.findById(id);
    }

    /**
     * 修改检查项
     * @param checkItem
     */
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }
}
