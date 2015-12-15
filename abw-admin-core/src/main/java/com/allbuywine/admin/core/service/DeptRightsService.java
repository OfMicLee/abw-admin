package com.allbuywine.admin.core.service;

import com.allbuywine.admin.bean.domain.DeptRights;
import com.allbuywine.admin.bean.domain.Rights;
import com.allbuywine.admin.core.mapper.DeptRightsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * MicLee on 14-7-2.
 * Description：部门权限Service
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DeptRightsService
{
    @Autowired
    private DeptRightsMapper deptRightsMapper;

    /**
     * 保存部门与权限的关系 of583
     * @param deptId 部门编号
     * @param rightsIds 权限编号列表
     */
    public void save(Integer deptId, List<Integer> rightsIds) {

        // 查询出库中已有的权限编号列表
        List<Integer> orgRightsIds = findRightsIdByDeptId(deptId);

        // 得到需要新增的关系
        List<Integer> addList = new ArrayList<Integer>();
        for (Integer rightsId : rightsIds) {
            if (!orgRightsIds.contains(rightsId)) {
                addList.add(rightsId);
            }
        }

        // 得到需要删除的关系
        List<Integer> delList = new ArrayList<Integer>();
        for (Integer orgRightsId : orgRightsIds) {
            if (!rightsIds.contains(orgRightsId)) {
                delList.add(orgRightsId);
            }
        }

        if (!CollectionUtils.isEmpty(addList)) {
            batchAdd(deptId, addList);
        }

        if (!CollectionUtils.isEmpty(delList)) {
            batchDelete(deptId, delList);
        }

    }

    /**
     * 批量添加部门权限
     *
     * MicLee(of730)
     * @param deptId 部门编号
     * @param rightsList 权限编号列表
     */
    public void batchAdd(Integer deptId, List<Integer> rightsList) {
        deptRightsMapper.batchAdd(deptId, rightsList);
    }

    /**
     * 删除单个部门对应的权限
     *
     * MicLee(of730)
     * @param deptRights
     */
    public void delete(DeptRights deptRights) {
        deptRightsMapper.delete(deptRights);
    }

    /**
     * 批量删除
     *
     * MicLee(of730)
     * @param deptId 部门id
     * @param rightsList 权限idList
     */
    public void batchDelete(Integer deptId, List<Integer> rightsList) {
        deptRightsMapper.batchDelete(deptId, rightsList);
    }

    /**
     * 根据部门id查询权限
     *
     * MicLee(of730)
     * @param deptId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Integer> findRightsIdByDeptId(Integer deptId) {
        return deptRightsMapper.findRightsIdListByDeptId(deptId);
    }

    /**
     * 根据部门id查询权限url
     *
     * MicLee(of730)
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<String> findAllRightsUrlsByDeptId(Integer id) {
        return deptRightsMapper.findRightsUrlByDeptId(id);
    }

    /**
     * 根据部门id查询权限列表
     *
     * MicLee(of730)
     * @param deptId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Rights> findRightsByDeptId(Integer deptId) {
        return deptRightsMapper.findRightsByDeptId(deptId);
    }
}
