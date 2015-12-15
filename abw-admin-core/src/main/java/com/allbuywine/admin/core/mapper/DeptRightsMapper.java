package com.allbuywine.admin.core.mapper;

import com.allbuywine.admin.bean.domain.DeptRights;
import com.allbuywine.admin.bean.domain.Rights;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MicLee on 14-7-2.
 * Description：部门权限的Mapper
 */
@Repository
public interface DeptRightsMapper
{
    /**
     * 保存部门对应的权限
     * 
     *
     * @param deptId     部门编号
     * @param rightsList 权限编号列表
     */
    void batchAdd (@Param("deptId") Integer deptId, @Param("list") List<Integer> rightsList);

    /**
     * 删除部门对应的权限
     *
     * @param deptRights
     */
    void delete (DeptRights deptRights);

    /**
     * 批量删除部门关联的权限
     * 
     *
     * @param deptId       部门id
     * @param rightsIdList 对于删除的权限
     */
    void batchDelete (@Param("deptId") Integer deptId, @Param("list") List<Integer> rightsIdList);

    /**
     * 根据部门id查询对应的权限id的List
     * 
     *
     * @param deptId
     * @return
     */
    List<Integer> findRightsIdListByDeptId (@Param("id") Integer deptId);


    /**
     * 根据部门获取非空url的url集合
     * 
     *
     * @param deptId
     * @return
     */
    List<String> findRightsUrlByDeptId (@Param("id") Integer deptId);

    /**
     * 根据部门id获取用户权限信息用于菜单展示
     * 
     *
     * @param deptId
     * @return
     */
    List<Rights> findRightsByDeptId (@Param("deptId") Integer deptId);

    /**
     * 菜单是否关联部门
     * 
     *
     * @param id
     * @return
     */
    Integer countRights (@Param("id") Integer id);

    /**
     * 查询部门关联菜单的数量
     * 
     *
     * @param id
     * @return
     */
    Integer countDepts (@Param("id") Integer id);
}
