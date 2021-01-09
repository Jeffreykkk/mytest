package com.alibaba.health.dao;

import com.alibaba.health.pojo.CheckGroup;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {
    /**
     * 添加检查组
     * @param checkgroup
     */
    void add(CheckGroup checkgroup);

    /**
     * 添加检查组与检查项关系
     * @param checkgroupId
     * @param checkitemId
     */
    void addCheckGroupCheckItem(@Param("checkgroupId") Integer checkgroupId,@Param("checkitemId") Integer checkitemId);

    /**
     * 检查组的分页查询
     * @param queryString
     * @return
     */
    Page<CheckGroup> findByCondition(String queryString);

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
     * 更新检查组
     * @param checkgroup
     */
    void update(CheckGroup checkgroup);

    /**
     * 删除检查组id与检查项id的绑定
     * @param id
     */
    void deleteCheckGroupCheckItem(Integer id);

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 查询检查组id被套餐使用的个数
     * @param id
     * @return
     */
    int findCountByCheckGroup(int id);


    /**
     * 通过id删除检查组
     * @param id
     */
    void deleteById(int id);
}
