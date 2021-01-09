package com.alibaba.health.service;

import com.alibaba.health.entity.PageResult;
import com.alibaba.health.entity.QueryPageBean;
import com.alibaba.health.exception.MyException;
import com.alibaba.health.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    /**
     * 添加检查组
     * @param checkgroup
     * @param checkitemIds
     */
    void add(CheckGroup checkgroup, Integer[] checkitemIds);

    /**
     * 检查组的分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    /**
     * 通过id查询检查组
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 通过检查组id查询选中的检查项id
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /**
     * 修改检查组
     * @param checkgroup
     * @param checkitemIds
     */
    void update(CheckGroup checkgroup, Integer[] checkitemIds);

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findALL();

    /**
     * 通过id删除检查组
     * @param id
     */
    void deleteById  (int id)throws MyException;
}
