package com.alibaba.health.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.health.dao.CheckGroupDao;
import com.alibaba.health.entity.PageResult;
import com.alibaba.health.entity.QueryPageBean;
import com.alibaba.health.exception.MyException;
import com.alibaba.health.pojo.CheckGroup;
import com.alibaba.health.service.CheckGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;
    /**
     * 添加检查组
     *
     * @param checkgroup   检查组信息
     * @param checkitemIds 选中的检查项id数组
     */
    @Override
    @Transactional
    public void add(CheckGroup checkgroup, Integer[] checkitemIds) {
        //（1）随后先添加检查组，获取检查组的id，（调用CheckGroupDao方法）
        checkGroupDao.add(checkgroup);
        //获取检查组的id
        Integer checkgroupId = checkgroup.getId();
        //（2）遍历选中的检查id的数组，以及Integer[]checkitemIds
        if(null != checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                //（3）添加检查组与检查项关系（调用CheckGroupDao方法）
                checkGroupDao.addCheckGroupCheckItem(checkgroupId,checkitemId);
            }
        }
      //添加事务控制
    }

    /**
     * 检查组的分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        // pageSize能无限大吗？参数是从前端来的，100W，后台限制大小
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 条件查询
        if(StringUtils.isNotEmpty(queryPageBean.getQueryString())){
            // 有查询条件， 模糊查询
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // page extends arrayList
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        PageResult<CheckGroup> pageResult = new PageResult<CheckGroup>(page.getTotal(),page.getResult());
        return pageResult;
    }

    /**
     * 通过id查询检查组
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    /**
     * 通过检查组id查询选中的检查项id
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    /**
     * 修改检查组
     * @param checkgroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void update(CheckGroup checkgroup, Integer[] checkitemIds) {
        //* 先更新检查组
        checkGroupDao.update(checkgroup);
        //* 先删除旧关系
        checkGroupDao.deleteCheckGroupCheckItem(checkgroup.getId());
        //* 遍历选中的检查项id的数组
        if(null != checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                //* 添加检查组与检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkgroup.getId(),checkitemId);
            }
        }
        //* 添加事务控制
    }

    /**
     * 查询所有检查组
     * @return
     */
    @Override
    public List<CheckGroup> findALL() {
        return checkGroupDao.findAll();
    }

    /**
     * 通过id删除检查组
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id) {
        //通过检查组查询id是否被使用
        int count =  checkGroupDao.findCountByCheckGroup(id);
        if (count > 0){
            throw new MyException("该检查组被套餐使用了，不能删除");
        }
        //没使用，删除检查组与套餐的关系
        checkGroupDao.deleteCheckGroupCheckItem(id);
        //删除检查组
        checkGroupDao.deleteById(id);



    }
}
